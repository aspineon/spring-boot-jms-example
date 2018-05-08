package com.chriniko.example.springbootjmsexample.service;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class QueueListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        try {

            TextMessage textMessage = (TextMessage) message;

            System.out.println("QueueListener#onMessage: message = " + textMessage.getText());

        } catch (JMSException e) {
            e.printStackTrace(System.err);
        }

    }
}
