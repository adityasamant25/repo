package com.training;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RestControllerEndpoint(id = "rest-weather")
public class RestWeatherEndpoint {

	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String invokeHtml() {
		return "<h1 style=\"color:red\">" + "frightful" + "</h1>";
	}
}
