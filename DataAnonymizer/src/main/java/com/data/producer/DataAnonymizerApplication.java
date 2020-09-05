package com.data.producer;

import com.data.producer.processor.Anonymizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataAnonymizerApplication {

    public static void main(String[] args) {

        SpringApplication.run(DataAnonymizerApplication.class, args);
        Anonymizer anonymizer = new Anonymizer();
        anonymizer.start();
    }
}
