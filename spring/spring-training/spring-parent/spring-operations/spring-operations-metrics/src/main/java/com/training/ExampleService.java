package com.training;

import java.util.Random;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;

@Service
public class ExampleService {

	Random random = new Random();
	public void call() {
		Metrics.counter("example.counter", Tags.empty()).increment();
		Metrics.gauge("example.gauge", this.random.nextDouble());
	}
}
