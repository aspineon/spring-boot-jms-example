package com.chriniko.example.springbootjmsexample.config;

import com.chriniko.example.springbootjmsexample.service.QueueListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String BROKER_USERNAME = "admin";
    private static final String BROKER_PASSWORD = "admin";

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setPassword(BROKER_USERNAME);
        connectionFactory.setUserName(BROKER_PASSWORD);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();

        template.setConnectionFactory(connectionFactory());
        template.setPubSubDomain(true); // Note: comment/uncomment for publish-subscribe.

        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency("1-20");
        factory.setPubSubDomain(true); // Note: comment/uncomment for publish-subscribe.
        factory.setTaskExecutor(taskExecutor());

        return factory;
    }

    @Bean
    public DefaultMessageListenerContainer customMessageListenerContainer(ConnectionFactory connectionFactory,
                                                                          QueueListener queueListener,
                                                                          @Value("${queues.sample}") final String destinationName) {

        DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();
        listener.setConnectionFactory(connectionFactory);
        listener.setDestinationName(destinationName);
        listener.setMessageListener(queueListener);

        listener.setErrorHandler(t -> System.out.println("General error occurred during the consumption of message: " + t));

        return listener;
    }

    @Bean
    public TaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor tE = new ThreadPoolTaskExecutor();

        tE.setCorePoolSize(5);
        tE.setMaxPoolSize(20);
        tE.setThreadNamePrefix("jms-thread-");
        tE.setKeepAliveSeconds(20);
        tE.setQueueCapacity(-1); // Note: in order to use synchronous queue.

        return tE;
    }
}
