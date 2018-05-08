package com.chriniko.example.springbootjmsexample;

import com.chriniko.example.springbootjmsexample.service.SimpleReceiver;
import com.chriniko.example.springbootjmsexample.service.SimpleSender;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SpringBootJmsExampleApplicationTests {

    @Autowired
    private SimpleSender simpleSender;

    @Autowired
    private SimpleReceiver simpleReceiver;

    @Value("${queues.sample}")
    private String sampleQueue;

    @Test
    public void contextLoads() throws Exception {

        //when
        simpleSender.send(sampleQueue, "Hello free world!");

        //then
        simpleReceiver.getLatch().await(1, TimeUnit.SECONDS);
        Assert.assertThat(simpleReceiver.getLatch().getCount(), Is.is(0L));

    }

}
