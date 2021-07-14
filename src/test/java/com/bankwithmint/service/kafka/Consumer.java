//package com.bankwithmint.sales.service.kafka;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.CountDownLatch;
//
///**
// * @author oluwatobi
// * @version 1.0
// * @date on 13/07/2021
// * inside the package - com.bankwithmint.sales.service.kafka
// */
//
//@Slf4j
//@Component
//public class Consumer {
//
//
//    private CountDownLatch latch = new CountDownLatch(1);
//    private String payload = null;
//
//    @KafkaListener(topics = "${test.topic}",  containerFactory = "kafkaListenerContainerFactory", autoStartup = "${listen.auto.start:false}")
//    public void receive(ConsumerRecord<?, ?> consumerRecord) {
//        log.info("received payload='{}'", consumerRecord.toString());
//        setPayload(consumerRecord.toString());
//        latch.countDown();
//    }
//
//    public CountDownLatch getLatch() {
//        return latch;
//    }
//
//    public void setLatch(CountDownLatch latch) {
//        this.latch = latch;
//    }
//
//    public String getPayload() {
//        return payload;
//    }
//
//    public void setPayload(String payload) {
//        this.payload = payload;
//    }
//}
