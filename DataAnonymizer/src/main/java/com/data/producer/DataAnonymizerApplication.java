package com.data.producer;

import com.data.producer.processor.Anonymizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DataAnonymizerApplication implements CommandLineRunner {

    @Autowired
    private Anonymizer anonymizer;

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(DataAnonymizerApplication.class);
        app.run(args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("hello world, I have just started up");
        anonymizer.start();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Spring boot standalone application is working...");
    }
}
