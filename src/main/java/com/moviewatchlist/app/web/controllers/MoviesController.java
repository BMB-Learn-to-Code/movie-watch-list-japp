package com.moviewatchlist.app.web.controllers;

import com.moviewatchlist.app.models.Movie;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class MoviesController {
    private final ArrayList<Movie> movies;
    private final ArrayList<Movie> watchedMovies;

    public MoviesController() {
        this.movies = new ArrayList<>();
        movies.add(new Movie("Inception", 2010L));
        movies.add(new Movie("The Matrix", 1999L));
        movies.add(new Movie("Interstellar", 2014L));
        movies.add(new Movie("The Shawshank Redemption", 1994L));
        movies.add(new Movie("The Godfather", 1972L));
        movies.add(new Movie("Dune 3", 2026L));
        this.watchedMovies = new ArrayList<>();
    }

    public void getAll(Context ctx) {
        ctx.json(movies).status(200);
    }
    public void getWatchedMovies(Context ctx) {
        ctx.json(watchedMovies).status(200);
    }
    public void getUpcomingMovies(Context ctx) {
        var upComingMovies = selectUpcomingMovies();
        ctx.json(upComingMovies).status(200);
    }

    public void watchMovies(Context ctx) {
        var movie = ctx.bodyAsClass(Movie.class);
        if (movie == null || movie.title() == null || movie.releaseDate() == null) {
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

    private List<Movie> selectUpcomingMovies() {
        return movies.stream().filter(movie -> movie.releaseDate() > 2025L).toList();
    }
}
