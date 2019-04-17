package com.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringOperationsLoggingApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringOperationsLoggingApplication.class);

		// In SpringBoot 1: application.setWebEnvironment(false);
		application.setWebApplicationType(WebApplicationType.NONE);

		application.run(args);

	}

}
