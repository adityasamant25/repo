package com.techprimers.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.techprimers.elastic.model.Users;

public interface UsersRepository extends ElasticsearchRepository<Users, Long> {

	List<Users> findByName(String text);

	List<Users> findBySalary(Long salary);

}
