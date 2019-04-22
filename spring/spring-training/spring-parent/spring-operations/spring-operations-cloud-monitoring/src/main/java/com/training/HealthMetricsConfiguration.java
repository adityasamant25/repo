package com.training;

import java.util.HashMap;
import java.util.List;

import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

@Configuration
class HealthMetricsConfiguration {
    // This should be a field so it doesn't get garbage collected
    private CompositeHealthIndicator healthIndicator;

    public HealthMetricsConfiguration(HealthAggregator healthAggregator,
                                      List<HealthIndicator> healthIndicators,
                                      MeterRegistry registry, ExampleHealthIndicator exampleIndicator) {

        healthIndicator = new CompositeHealthIndicator(healthAggregator, new HashMap<String, HealthIndicator>());

        healthIndicator.getRegistry().register("1", exampleIndicator);

        // presumes there is a common tag applied elsewhere that adds tags for app, etc.
        registry.gauge("health", Tags.empty(), healthIndicator, health -> {
            Status status = health.health().getStatus();
            switch (status.getCode()) {
                case "UP":
                    return 0;
                case "OUT_OF_SERVICE":
                    return 1;
                case "DOWN":
                    return 2;
                case "UNKNOWN":
                default:
                    return 3;
            }
        });
    }
}
