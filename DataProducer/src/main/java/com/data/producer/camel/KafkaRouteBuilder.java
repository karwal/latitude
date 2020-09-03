package com.data.producer.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaRouteBuilder extends RouteBuilder {

    public static int messagesProcessed;

    @Value("${kafkaInputTopic}")
    private String kafkaInputTopic;
    @Value("${kafkaBrokerHost}")
    private String kafkaBrokerHost;
    @Value("${kafkaBrokerPort}")
    private String kafkaBrokerPort;

    Processor countProcessor = new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
            messagesProcessed++;
            if(messagesProcessed % 10000 == 0) {
                System.out.println("messagesProcessed = " +messagesProcessed);
            }
        }
    };
    @Override
    public void configure() throws Exception {
        System.out.println("configure Called ......... =");
        //from("stream:file?fileName=./SampleInput.txt&scanStream=true&scanStreamDelay=1000")
        from("file:.?fileName=SampleInput.txt")
                .split(body().tokenize("\n"))
                .streaming()
                .setHeader("Key", constant("Camel"))
                .process(countProcessor)
                //.to("kafka:input_topic?brokers=localhost:9092");
                .to(String.format("kafka:%s?brokers=%s:%s", kafkaInputTopic, kafkaBrokerHost, kafkaBrokerPort));
    }

    public static int getMessagesProcessed() {
        return messagesProcessed;
    }

    public static void setMessagesProcessed(int messagesProcessed) {
        KafkaRouteBuilder.messagesProcessed = messagesProcessed;
    }

    public String getKafkaInputTopic() {
        return kafkaInputTopic;
    }

    public void setKafkaInputTopic(String kafkaInputTopic) {
        this.kafkaInputTopic = kafkaInputTopic;
    }

    public String getKafkaBrokerHost() {
        return kafkaBrokerHost;
    }

    public void setKafkaBrokerHost(String kafkaBrokerHost) {
        this.kafkaBrokerHost = kafkaBrokerHost;
    }

    public String getKafkaBrokerPort() {
        return kafkaBrokerPort;
    }

    public void setKafkaBrokerPort(String kafkaBrokerPort) {
        this.kafkaBrokerPort = kafkaBrokerPort;
    }

    public Processor getCountProcessor() {
        return countProcessor;
    }

    public void setCountProcessor(Processor countProcessor) {
        this.countProcessor = countProcessor;
    }
}

