package com.spring.elastic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.elastic.dao.MovieRepository;
import com.spring.elastic.model.Movie;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public List<Movie> getByName(String name) {
        return repository.findByName(name);
    }

    public List<Movie> getByRatingInterval(Double beginning, Double end) {
        return repository.findByRatingBetween(beginning, end);
    }

    public void addMovie(Movie movie) {
        repository.save(movie);
    }
}
