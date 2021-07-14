package com.bankwithmint.sales;

import com.bankwithmint.sales.data.models.Product;
import com.bankwithmint.sales.data.repository.ProductRepository;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@EnableAsync
public class SalesManagementApplication implements CommandLineRunner {

	@Autowired
	ProductRepository productRepository;


	public static void main(String[] args) {
		SpringApplication.run(SalesManagementApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

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
		return TopicBuilder.name("order-report")
				.partitions(10)
				.replicas(1)
				.build();
	}

}
