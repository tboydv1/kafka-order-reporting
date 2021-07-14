package com.bankwithmint.sales.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.config
 */


@Configuration
@PropertySource("classpath:application.yml")
public class KafkaProducerConfig {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("order-report")
                .partitions(10)
                .replicas(1)
                .build();
    }

}
