package com.chriniko.example.springbootjmsexample;

import com.chriniko.example.springbootjmsexample.service.SimpleSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJmsExampleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJmsExampleApplication.class, args);
    }

    @Autowired
    private SimpleSender simpleSender;

    @Value("${queues.sample}")
    private String sampleQueue;

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i < 20; i++) {
            simpleSender.send(sampleQueue, "Test Message " + i);
        }
    }
}
