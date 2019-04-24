package com.training;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class ExampleController {

	private final ExampleService service;
	
	public ExampleController(ExampleService service, MeterRegistry meterRegistry) {
		this.service = service;
	}
	
	@RequestMapping("/")
	public String hello() {
		this.service.call();
		return "Hello World!";
	}
}
