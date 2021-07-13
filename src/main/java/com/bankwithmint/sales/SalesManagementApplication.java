package com.bankwithmint.sales;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SalesManagementApplication {

	@Value("${topic:order-report}")
	private String topic;

	public static void main(String[] args) {
		SpringApplication.run(SalesManagementApplication.class, args);
	}


}
