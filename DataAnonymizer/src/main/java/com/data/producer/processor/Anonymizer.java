package com.data.producer.processor;

import com.data.producer.model.Customer;
import com.opencsv.CSVReader;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;


public class Anonymizer {

    private static long messageProcessed;

    public void start() {
        try {
            System.out.println("Anonymizer .................");
            Properties props = new Properties();
            props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
            props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
            props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

            final StreamsBuilder builder = new StreamsBuilder();

            KStream<String, String> source = builder.stream("input");
            source.mapValues(value -> anonymize(value))
                    .to("output", Produced.with(Serdes.String(), Serdes.String()));

            final Topology topology = builder.build();

            final KafkaStreams streams = new KafkaStreams(topology, props);
            final CountDownLatch latch = new CountDownLatch(1);

            addShutDownHook(streams, latch);

            try {
                streams.start();
                latch.await();
            } catch (Throwable e) {
                System.exit(1);
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addShutDownHook(KafkaStreams streams, CountDownLatch latch) {
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                System.out.println("ShutDownHook .................");
                streams.close();
                latch.countDown();
            }
        });
    }

    public String anonymize(String inputData) {
        try {
            messageProcessed++;
            CSVReader reader = new CSVReader(new StringReader(inputData));
            CsvToBean<Customer> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Customer.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<Customer> customers = csvToBean.parse();
            if (customers.size() >= 1) {
                //System.out.println("Customer == " + customer.toString());
                Customer anonymizedCustomer = customers.get(0).anonymize();
                //System.out.println("Anonymized == " +anonymizedCustomers.toString());
                StringWriter writer = new StringWriter();
                ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
                mappingStrategy.setType(Customer.class);

                StatefulBeanToCsvBuilder<Customer> builder = new StatefulBeanToCsvBuilder(writer);
                StatefulBeanToCsv<Customer> beanWriter = builder
                        .withMappingStrategy(mappingStrategy)
                        .withSeparator(',')
                        .withQuotechar('\"')
                        .withLineEnd(ICSVWriter.DEFAULT_LINE_END)
                        .build();
                beanWriter.write(anonymizedCustomer);
                String returnString = writer.toString();
                writer.close();
                reader.close();
                if (messageProcessed % 10000 == 0) {
                    System.out.println(" Messages Processed : " + messageProcessed);
                }
                return returnString;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to convert : " + inputData + " " + e.getMessage();
        }

        return null;
    }
}
