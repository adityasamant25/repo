package com.training;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Car {

	@Id
	private BigInteger id;

	private String make;

	private String model;

	private int year;

	@GeoSpatialIndexed(name = "position")
	private Point position;

	public Car(String make, String model, int year, Point position) {
		super();
		this.model = model;
		this.make = make;
		this.year = year;
		this.position = position;
	}

	public String getModel() {
		return model;
	}

	public String getMake() {
		return make;
	}

	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", model=" + model + ", make=" + make + ", year=" + year + ", position=" + position
				+ "]";
	}

}
