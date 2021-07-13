package com.bankwithmint.sales.config.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.AbstractKafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.config.kafka
 */

@Component
class Customizer {

    Customizer(AbstractKafkaListenerContainerFactory<?, ?, ?> factory,
               @Value("${start.containers:false}") boolean start) {

        factory.setAutoStartup(start);
    }


}
