package com.data.producer.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaRouteBuilder extends RouteBuilder {

    public static int messagesProcessed;

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
        System.out.println("configure Called .........");
        from("kafka:output_topic?brokers=localhost:9092")
                .process(countProcessor)
                .to("file:.?fileName=ProcessedOutput.txt&fileExist=Append");
    }
}

