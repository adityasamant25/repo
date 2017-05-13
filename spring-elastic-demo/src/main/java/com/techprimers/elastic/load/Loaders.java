package com.techprimers.elastic.load;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techprimers.elastic.model.Users;
import com.techprimers.elastic.repository.UsersRepository;

@Component
public class Loaders {

	@Autowired
	ElasticsearchOperations operations;
	
	@Autowired
	UsersRepository usersRepository;
	
	@PostConstruct
	@Transactional
	public void loadAll() {

		operations.putMapping(Users.class);
		System.out.println("Loading data ..");
		usersRepository.save(getData());
		System.out.println("Loading completed ..");
	}

	private List<Users> getData() {
		
		List<Users> users = new ArrayList<>();
		users.add(new Users("Aditya", 123L, "Accounting", 12000L));
		users.add(new Users("Isha", 1234L, "Finance", 22000L));
		users.add(new Users("Bunny", 1235L, "Accounting", 12000L));
		return users;
	}
}
