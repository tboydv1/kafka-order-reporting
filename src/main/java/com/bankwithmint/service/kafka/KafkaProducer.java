package com.bankwithmint.service.kafka;

import com.bankwithmint.reporting.data.ReportData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.service.kafka
 */

@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, ReportData> kafkaTemplate;

    @Async
    public void send(String topic, ReportData payload) {
        log.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}
