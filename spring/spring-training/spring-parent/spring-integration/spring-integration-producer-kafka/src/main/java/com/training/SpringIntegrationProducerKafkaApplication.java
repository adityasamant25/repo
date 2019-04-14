package com.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableBinding(MessageChannels.class)
@RestController
@SpringBootApplication
public class SpringIntegrationProducerKafkaApplication {

	private final MessageChannels channels;
	
	@Autowired
	public SpringIntegrationProducerKafkaApplication(MessageChannels channel) {
		this.channels = channel;
	}
	
	@GetMapping(value = "/greet/{name}")
	void greet(@PathVariable String name) {
		Message<String> msg = MessageBuilder.withPayload(name).build();
		this.channels.output().send(msg);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationProducerKafkaApplication.class, args);
	}

}

interface MessageChannels {
	
	@Output
	MessageChannel output();
}