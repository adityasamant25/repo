package com.techprimers.elastic.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techprimers.elastic.model.Users;
import com.techprimers.elastic.repository.UsersRepository;

@RestController
@RequestMapping("/rest/search")
public class SearchResource {

	@Autowired
	UsersRepository usersRepository;

	@GetMapping(value = "/name/{text}")
	public List<Users> searchByName(@PathVariable final String text) {
		return usersRepository.findByName(text);
	}
	
	@GetMapping(value = "/salary/{salary}")
	public List<Users> searchBySalary(@PathVariable final Long salary) {
		return usersRepository.findBySalary(salary);
	}
	
	@GetMapping(value = "/all")
	public List<Users> searchAll() {
		Iterable<Users> users = usersRepository.findAll();
		List<Users> listOfUsers = new ArrayList<>();
		users.forEach(listOfUsers::add);
		return listOfUsers;
	}
}
