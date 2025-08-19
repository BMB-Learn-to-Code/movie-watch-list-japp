package com.moviewatchlist.app.web.controllers;

import com.moviewatchlist.app.domain.Movie;
import com.moviewatchlist.app.service.MovieService;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoviesController {
    private final MovieService movieService;
    private final ArrayList<Movie> watchedMovies;

    public MoviesController() {
        movieService = new MovieService();
        this.watchedMovies = new ArrayList<>();
    }

    public void getAll(Context ctx) {
        try {
            var movies = movieService.getAllMovies();
            if (movies.isEmpty()) {
                ctx.status(404).result("No movies found");
            } else {
                ctx.json(movies).status(200);
            }
        } catch (SQLException e) {
            ctx.json(e.getMessage()).status(500);
        }
    }
    public void getWatchedMovies(Context ctx) {
        ctx.json(watchedMovies).status(200);
    }
    public void getUpcomingMovies(Context ctx) {
        try {
            var upComingMovies = selectUpcomingMovies();
            ctx.json(upComingMovies).status(200);
        } catch (SQLException e) {
           ctx.json(e.getMessage()).status(500);
        }
    }

    public void watchMovies(Context ctx) {
        List<Movie> movies = List.of();
        try{
            movies = movieService.getAllMovies();
        } catch (SQLException e) {
           ctx.json(e.getMessage()).status(500);
        }

        var movie = ctx.bodyAsClass(Movie.class);

        if (movie == null || movie.title() == null || movie.releaseTimestamp() == null) {
            ctx.status(400).result("Invalid movie data");
            return;
        }

        if (movies.stream().anyMatch(m -> m.title().equals(movie.title()))) {
            ctx.status(409).result("Movie already watched");
            return;
        }
        watchedMovies.add(movie);
        ctx.status(201);
    }

    private List<Movie> selectUpcomingMovies() throws SQLException {
        List<Movie> movies;
        try{
            movies = movieService.getAllMovies();
        } catch (SQLException e) {
            throw new SQLException("Error fetching movies: " + e.getMessage());
        }
        return movies.stream().filter(movie -> movie.releaseTimestamp() > 2025L).toList();
    }
}
