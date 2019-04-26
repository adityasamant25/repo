package com.training;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringEdgeGreetingsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEdgeGreetingsServiceApplication.class, args);
	}

}

@RestController
class GreetingsRestController {

	@GetMapping(value = "/greetings/{name}")
	Map<String, String> greeting(@PathVariable String name, @RequestHeader("x-forwarded-host") Optional<String> host,
			@RequestHeader("x-forwarded-port") Optional<String> port) {
		host.ifPresent(h -> System.out.println("host = " + h));
		port.ifPresent(p -> System.out.println("port = " + p));
		return Collections.singletonMap("greeting", "Hello, " + name + "!");
	}
}
