package com.spring.elastic.dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.spring.elastic.model.Movie;

public interface MovieRepository extends ElasticsearchRepository<Movie, String> {
    public List<Movie> findByName(String name);

    public List<Movie> findByRatingBetween(Double beginning, Double end);
}
