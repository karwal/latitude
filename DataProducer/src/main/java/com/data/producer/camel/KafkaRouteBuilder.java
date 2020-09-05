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
            if (messagesProcessed % 10000 == 0) {
                System.out.println("messagesProcessed = " + messagesProcessed);
            }
        }
    };

    @Override
    public void configure() throws Exception {
        System.out.println("configure Called ......... =");
        from("file:.?fileName=SampleInput.txt")
                .split(body().tokenize("\n"))
                .streaming()
                .process(countProcessor)
                .to(String.format("kafka:%s?brokers=%s:%s", kafkaInputTopic, kafkaBrokerHost, kafkaBrokerPort));
    }

}

