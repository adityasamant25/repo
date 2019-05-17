package com.training;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/oauth_login").permitAll().anyRequest().authenticated().and()
				.oauth2Login().loginPage("/oauth_login").defaultSuccessUrl("/loginSuccess")
	            .failureUrl("/loginFailure");
	}
}
