package com.moviewatchlist.app.service;

import com.moviewatchlist.app.domain.movie.Movie;
import com.moviewatchlist.app.domain.movie.MovieRequestBody;
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

    public void saveMovie(MovieRequestBody movie) throws SQLException {
        movieRepository.saveMovie(movie);
    }
    public List<Movie> getAllWatchedMovies(String name) throws SQLException {
        return movieRepository.findAllWatchedMovies(name);
    }

    public List<Movie> getAllUpcomingMovies() throws SQLException {
        return movieRepository.findAllUpcomingMovies();
    }
}
