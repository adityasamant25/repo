package com.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ExampleController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@RequestMapping("/")
	public String hello()
	{
		return jdbcTemplate.queryForObject("select model from car where id = 1", String.class);
	}
	
}
