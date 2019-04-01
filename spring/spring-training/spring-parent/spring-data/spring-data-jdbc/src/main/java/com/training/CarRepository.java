package com.training;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepository {

	private final JdbcTemplate jdbc;

	public CarRepository(JdbcTemplate jdbc) {
		super();
		this.jdbc = jdbc;
	}

	public List<Car> findByMakeIgnoringCase(String make) {
		return this.jdbc.query("select * from car where upper(car.make) = UPPER(?) order by id",
				(rs, i) -> new Car(rs.getInt("id"), rs.getString("make"), rs.getString("model"), rs.getInt("year")),
				make);
	}

}
