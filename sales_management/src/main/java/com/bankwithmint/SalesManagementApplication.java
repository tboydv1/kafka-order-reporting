package com.bankwithmint;

import com.bankwithmint.reporting.data.ReportData;
import com.bankwithmint.data.models.Product;
import com.bankwithmint.data.repository.ProductRepository;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableAsync
public class SalesManagementApplication implements CommandLineRunner {

	@Autowired
	ProductRepository productRepository;

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;


	public static void main(String[] args) {
		SpringApplication.run(SalesManagementApplication.class, args);
	}


	@Override
	public void run(String... args) {

		List<Product> products = List.of(new Product("Hammer",  1400.00, 24,"this is a Big Hammer"),
				new Product( "Sledge hammer",  1400.00, 27,"this is a Sledge Hammer"),
				new Product( "Blinds",  1400.00, 24,"this is a Window Blind"),
				new Product( "Speaker",  1400.00, 24,"this is a Surround Speaker"),
				new Product( "Roof Tops", 1400.00, 24,"this is a Roof Top")
		);

		products.forEach(product -> {
			productRepository.save(product);
		});

	}

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("sales")
				.partitions(10)
				.replicas(1)
				.build();
	}

	@Bean
	public ProducerFactory<String, ReportData> reportDataFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, ReportData> greetingKafkaTemplate() {
		return new KafkaTemplate<>(reportDataFactory());
	}


}
