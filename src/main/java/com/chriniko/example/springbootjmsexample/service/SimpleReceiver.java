package com.chriniko.example.springbootjmsexample.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class SimpleReceiver  {

    private final CountDownLatch latch = new CountDownLatch(1);

    @JmsListener(destination = "${queues.sample}")
    @SendTo(value = "${queues.monitor}")
    public String listen(String message) {

        System.out.println("[ThreadName = "
                + Thread.currentThread().getName()
                + "]"
                + "SimpleReceiver#receive: " + message);

        latch.countDown();

        return message;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
