package com.data.producer.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


public class FileRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        System.out.println("configure Called .........");
        from("file:src/main/resources?fileName=sample.input.txt").to("stream:out");
    }
}
