package com.data.producer.processor;

import com.data.producer.model.Customer;
import com.opencsv.CSVReader;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.*;
import org.springframework.stereotype.Component;
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class AnonymizationProcessor /*implements Processor */{
//    public static int messagesProcessed;
//
//    @Override
//    public void process(Exchange exchange) throws Exception {
//        messagesProcessed++;
//        if(messagesProcessed % 10000 == 0) {
//            System.out.println(String.format("messagesProcessed = %s", messagesProcessed));
//        }
//
//        String csvRecord = exchange.getIn().getBody().toString();
//        CSVReader reader = new CSVReader(new StringReader(csvRecord));
//
//        CsvToBean<Customer> csvToBean = new CsvToBeanBuilder(reader)
//                .withType(Customer.class)
//                .withIgnoreLeadingWhiteSpace(true)
//                .build();
//        Iterator<Customer> csvCustomerIterator = csvToBean.iterator();
//        List<Customer> anonymizedCustomers = new ArrayList<>(1);
//        while (csvCustomerIterator.hasNext()) {
//            Customer customer = csvCustomerIterator.next();
//            //System.out.println("Customer == " + customer.toString());
//            anonymizedCustomers.add(customer.anonymize());
//            //System.out.println("Anonymized == " +anonymizedCustomers.toString());
//        }
//        StringWriter writer = new StringWriter();
//        ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
//        mappingStrategy.setType(Customer.class);
//
//        StatefulBeanToCsvBuilder<Customer> builder = new StatefulBeanToCsvBuilder(writer);
//        StatefulBeanToCsv<Customer> beanWriter = builder
//                .withMappingStrategy(mappingStrategy)
//                .withSeparator(',')
//                .withQuotechar('\"')
//                .withLineEnd(ICSVWriter.DEFAULT_LINE_END)
//                .build();
//        beanWriter.write(anonymizedCustomers);
//        exchange.getIn().setBody(writer.toString());
//        writer.close();
//        reader.close();
//
//    }
}
