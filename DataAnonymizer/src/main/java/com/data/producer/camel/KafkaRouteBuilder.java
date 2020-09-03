package com.data.producer.camel;

import com.data.producer.processor.AnonymizationProcessor;
import com.opencsv.CSVReader;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.List;

@Component
public class KafkaRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        System.out.println("configure Called .........");
        from("kafka:input_topic?brokers=localhost:9092")
                .process(new AnonymizationProcessor())
                .to("kafka:output_topic?brokers=localhost:9092");
    }
}

