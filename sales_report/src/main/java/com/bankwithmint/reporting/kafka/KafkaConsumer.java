package com.bankwithmint.reporting.kafka;

import com.bankwithmint.reporting.data.ReportData;
import com.bankwithmint.reporting.data.ReportDataRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.service.kafka
 */

@Slf4j
@Component
@Data
public class KafkaConsumer  {

    private CountDownLatch latch = new CountDownLatch(5);

    @Autowired
    ReportDataRepository reportDataRepository;


    @KafkaListener(topics = "sales",
                    groupId = "bankwithmint",
                    containerFactory = "kafkaListenerContainerFactory")
    public void consume(ReportData payload) {
        log.info("Received Message in group bankwithmink: " + payload);
        reportDataRepository.save(payload);
        latch.countDown();
    }



}
