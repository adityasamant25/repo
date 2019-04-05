package com.training;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringRestErrorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestErrorsApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PersonRepository personRepository) {
		return args -> {
			Arrays.asList("Phil", "Josh")
					.forEach(name -> personRepository.save(new Person(name, (name + "@email.com").toLowerCase())));
			personRepository.findAll().forEach(System.out::println);
			personRepository.findByEmail("josh@email.com").forEach(System.err::println);
		};
	}

}
