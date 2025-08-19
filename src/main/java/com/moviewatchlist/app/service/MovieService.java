package com.moviewatchlist.app.service;

import com.moviewatchlist.app.domain.Movie;
import com.moviewatchlist.app.repository.MovieRepository;

import java.sql.SQLException;
import java.util.List;

public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService() {
        this.movieRepository = new MovieRepository();
    }

    public List<Movie> getAllMovies() throws SQLException {
        return movieRepository.findAll();
    }
}
