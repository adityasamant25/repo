package com.training;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "weather")
public class WeatherEndpoint {

	@ReadOperation
	public String checkWeather() {
		return "frightful";
	}
}
