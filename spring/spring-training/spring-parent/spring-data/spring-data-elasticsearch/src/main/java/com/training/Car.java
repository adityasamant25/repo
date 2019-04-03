package com.training;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "car", type = "car", shards = 1, replicas = 0, refreshInterval = "-1")
public class Car {

	@Id
	private Long id;

	private String make;

	private String model;

	private int year;

	Car() {

	}

	public Car(Long id, String make, String model, int year) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.year = year;
	}

	public Long getId() {
		return id;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", make=" + make + ", model=" + model + ", year=" + year + "]";
	}

}
