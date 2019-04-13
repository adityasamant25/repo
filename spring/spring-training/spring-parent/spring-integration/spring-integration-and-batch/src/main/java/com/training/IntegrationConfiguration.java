package com.training;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.dsl.Files;
import org.springframework.stereotype.Component;

@Configuration
public class IntegrationConfiguration {

	@Bean
	IntegrationFlow incomingFiles(@Value("${person.property.inputDir}") File dir) {
		return IntegrationFlows
				.from(Files.inboundAdapter(dir).preventDuplicates(true).autoCreateDirectory(true),
						poller -> poller.poller(spec -> spec.fixedRate(1, TimeUnit.SECONDS)))
				.handle(File.class, (payload, headers) -> {
					System.out.println("we have seen payload at: " + payload.getAbsolutePath());
					return null;
				}).get();
	}
}

@Component
@ConfigurationProperties("person.property")
class PersonProperties {

	private String inputDir;

	public String getInputDir() {
		return inputDir;
	}

	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

}