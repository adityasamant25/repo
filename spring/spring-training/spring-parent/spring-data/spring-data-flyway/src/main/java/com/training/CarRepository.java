package com.training;

import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {

	public Iterable<Car> findByMakeIgnoringCase(String make);
}
