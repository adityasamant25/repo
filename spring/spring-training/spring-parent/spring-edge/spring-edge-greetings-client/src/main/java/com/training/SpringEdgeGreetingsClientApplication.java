package com.training;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class SpringEdgeGreetingsClientApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringEdgeGreetingsClientApplication.class, args);
	}

}

@RestController
class GreetingsApiGatewayRestController {

	private final RestTemplate restTemplate;

	@Autowired
	public GreetingsApiGatewayRestController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/hi/{name}")
	String greet(@PathVariable String name) {
		ResponseEntity<Greeting> responseEntity = this.restTemplate.exchange("http://greetings-service/greetings/{name}", HttpMethod.GET, null,
				Greeting.class, name);
		return responseEntity.getBody().getGreeting();
	}
}

class Greeting {
	private String greeting;

	public String getGreeting() {
		return greeting;
	}

}

//@Component
class RateLimitingZuulFilter extends ZuulFilter {

	private final RateLimiter rateLimiter = RateLimiter.create(1.0 / 30.0);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {

		try {
			RequestContext currentContext = RequestContext.getCurrentContext();
			HttpServletResponse response = currentContext.getResponse();

			if (!this.rateLimiter.tryAcquire()) {
				response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
				response.getWriter().append(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
				currentContext.setSendZuulResponse(false);
			}
		} catch (IOException e) {
			ReflectionUtils.rethrowRuntimeException(e);
		}

		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 100;
	}

}
