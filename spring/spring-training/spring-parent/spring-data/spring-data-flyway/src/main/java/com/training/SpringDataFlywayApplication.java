package com.training;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDataFlywayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataFlywayApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner findByMake(CarRepository repository) {
		return args -> repository.findByMakeIgnoringCase("HONDA").forEach(System.err::println);
	}
}
