package com.training;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringIntegrationBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationBatchApplication.class, args);
	}

}
