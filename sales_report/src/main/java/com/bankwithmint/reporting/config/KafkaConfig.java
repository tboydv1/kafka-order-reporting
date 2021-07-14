package com.bankwithmint.reporting.config;

import com.bankwithmint.reporting.data.ReportData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 14/07/2021
 * inside the package - com.bankwithmint.reporting.config
 */

@EnableKafka
@Configuration
public class KafkaConfig {


    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;


    public ConsumerFactory<String, ReportData> reportDataConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "bankwithmint");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(ReportData.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReportData> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReportData> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reportDataConsumerFactory());
        factory.getContainerProperties().setIdleBetweenPolls(10000L);
        return factory;
    }


}
