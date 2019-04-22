package com.training;

import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.actuate.health.Status;

//@Component
public class ExampleHealthAggregator extends OrderedHealthAggregator {

	public ExampleHealthAggregator() {
		setStatusOrder(Status.UP, Status.OUT_OF_SERVICE, Status.DOWN, Status.UNKNOWN);
	}
	
}
