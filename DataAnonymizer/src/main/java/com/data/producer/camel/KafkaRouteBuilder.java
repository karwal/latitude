package com.data.producer.camel;

import com.data.producer.processor.AnonymizationProcessor;
import com.opencsv.CSVReader;
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.StringReader;
import java.util.List;

//@Component
public class KafkaRouteBuilder /*extends RouteBuilder*/ {

    @Value("${kafkaOutputTopic}")
    private String kafkaOutputTopic;
    @Value("${kafkaInputTopic}")
    private String kafkaInputTopic;
    @Value("${kafkaBrokerHost}")
    private String kafkaBrokerHost;
    @Value("${kafkaBrokerPort}")
    private String kafkaBrokerPort;

//    @Autowired
//    Processor anonymizationProcessor;
//
//    String kafkaFromRoute;
//    String kafkaToRoute;

    @PostConstruct
    public void afterPropertiesSet() {
//        System.out.println("afterPropertiesSet .........");
//        kafkaFromRoute = String.format("kafka:%s?brokers=%s:%s&autoOffsetReset=earliest&maxPollRecords=2000", kafkaInputTopic, kafkaBrokerHost, kafkaBrokerPort);
//        kafkaToRoute = String.format("kafka:%s?brokers=%s:%s&producerBatchSize=65536", kafkaOutputTopic, kafkaBrokerHost, kafkaBrokerPort);
//        anonymizationProcessor = new AnonymizationProcessor();
    }

    //@Override
    public void configure() throws Exception {
//        System.out.println("configure Called .........");
//        from(kafkaFromRoute)
//            .process(anonymizationProcessor)
//            .to(kafkaToRoute);
    }
}

