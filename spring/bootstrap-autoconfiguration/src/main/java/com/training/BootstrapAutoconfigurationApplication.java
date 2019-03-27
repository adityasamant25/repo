package com.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class BootstrapAutoconfigurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootstrapAutoconfigurationApplication.class, args);
	}

}
