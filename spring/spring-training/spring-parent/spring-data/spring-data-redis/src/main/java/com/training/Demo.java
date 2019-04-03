package com.training;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Demo implements CommandLineRunner {

	private static Log logger = LogFactory.getLog(Demo.class);
	private final StringRedisTemplate template;
	private final SlowService service;

	public Demo(StringRedisTemplate template, SlowService service) {
		super();
		this.template = template;
		this.service = service;
	}

	@Override
	public void run(String... args) throws Exception {
		reset();
		caching();
	}

	private void reset() {
		this.template.delete(Arrays.asList("boot"));
	}

	private void caching() {
		logger.info("----> 1 " + this.service.execute("boot"));
		logger.info("----> 2 " + this.service.execute("boot"));
		logger.info("----> 3 " + this.service.execute("boot"));
	}
}
