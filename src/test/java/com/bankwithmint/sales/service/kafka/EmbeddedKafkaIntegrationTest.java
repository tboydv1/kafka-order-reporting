package com.bankwithmint.sales.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.service.kafka
 */

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions=1, brokerProperties={"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@Slf4j
class EmbeddedKafkaIntegrationTest {

    @Autowired
    private Producer producer;

    @Autowired
    private Consumer consumer;


    @Value("${test.topic}")
    private String topic;


    @BeforeEach
    void setup(){
        assertNotNull(producer);
        assertNotNull(consumer);
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingtoSimpleProducer_thenMessageReceived()
            throws Exception {

        log.info("Topic value ==> {}", topic);
        assertThat(topic, notNullValue());

        producer.send(topic, "This is a test message from the KafkaProducer");
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);

        assertThat(consumer.getLatch().getCount(), equalTo(0L));
        assertThat(consumer.getPayload(), containsString("embedded-test-topic"));
    }
}