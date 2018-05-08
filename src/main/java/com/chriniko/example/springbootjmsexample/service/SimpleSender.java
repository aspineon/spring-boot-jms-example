package com.chriniko.example.springbootjmsexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SimpleSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination, String message) {

        System.out.printf("[ThreadName = "
                + Thread.currentThread().getName()
                + "]"
                + " --- SimpleSender#send ---> sending message='{%s}' to destination='{%s}'\n", message, destination);

        jmsTemplate.convertAndSend(destination, message);
    }
}
