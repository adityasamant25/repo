package com.training;

import org.elasticsearch.index.query.QueryBuilders;
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
