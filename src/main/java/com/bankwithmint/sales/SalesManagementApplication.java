package com.bankwithmint.sales;

import com.bankwithmint.sales.data.models.Product;
import com.bankwithmint.sales.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

		List<Product> products = List.of(new Product("Hammer",  BigDecimal.valueOf(1400), 24,"this is a Big Hammer"),
				new Product( "Sledge hammer",  BigDecimal.valueOf(1500), 27,"this is a Sledge Hammer"),
				new Product( "Blinds",  BigDecimal.valueOf(2400), 24,"this is a Window Blind"),
				new Product( "Speaker",  BigDecimal.valueOf(1400), 24,"this is a Surround Speaker"),
				new Product( "Roof Tops",  BigDecimal.valueOf(1400), 24,"this is a Roof Top")
		);

		products.forEach(product -> {
			productRepository.save(product);
		});

	}
}
