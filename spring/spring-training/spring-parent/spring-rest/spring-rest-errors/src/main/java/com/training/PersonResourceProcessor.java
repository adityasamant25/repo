package com.training;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class PersonResourceProcessor implements ResourceProcessor<Resource<Person>> {

	@Override
	public Resource<Person> process(Resource<Person> resource) {
		String id = Long.toString(resource.getContent().getId());
		String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("people/{id}/photo").buildAndExpand(id)
				.toUriString();
		resource.add(new Link(uri, "photo"));
		return resource;
	}

}
