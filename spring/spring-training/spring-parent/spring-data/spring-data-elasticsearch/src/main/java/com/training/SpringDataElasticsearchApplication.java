package com.training;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

@SpringBootApplication
public class SpringDataElasticsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataElasticsearchApplication.class, args);
	}

	@SuppressWarnings("resource")
	@Bean
	public Client client() {
		Client client = null;
		Settings settings = Settings.builder().put("cluster.name", "docker-cluster").build();
		try {
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}

	@Bean
	public InitializingBean seedDatabase(CarRepository repository) {
		return () -> {
			System.out.println("Seeding database");
			repository.deleteAll();
			repository.save(new Car(Long.valueOf(1), "Honda", "Civic", 1997));
			repository.save(new Car(Long.valueOf(2), "Honda", "Accord", 2003));
			repository.save(new Car(Long.valueOf(3), "Ford", "Escort", 1985));
		};
	}

	@Bean
	public CommandLineRunner example(CarRepository repository, ElasticsearchTemplate template) {
		return (args) -> {
			System.err.println("From the repository...");
			repository.findByMakeIgnoringCase("Honda").forEach(System.err::println);

			System.err.println("\nFrom the template...");
			;
			SearchQuery query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.fuzzyQuery("make", "Ronda"))
					.build();
			template.queryForList(query, Car.class).forEach(System.err::println);
		};
	}
}
