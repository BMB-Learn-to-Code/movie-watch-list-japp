package com.moviewatchlist.app.web.controllers;

import com.moviewatchlist.app.domain.MovieRequestBody;
import com.moviewatchlist.app.service.MovieService;
import io.javalin.http.Context;

import java.sql.SQLException;

public class MoviesController {
    private final MovieService movieService;

    public MoviesController() {
        movieService = new MovieService();
    }

    public void getAll(Context ctx) {
        try {
            var movies = movieService.getAllMovies();
            if (movies.isEmpty()) {
                ctx.status(404).result("No movies found");
            }
            ctx.json(movies).status(200);

        } catch (SQLException e) {
            ctx.json(e.getMessage()).status(500);
        }
    }
    public void getAllWatched(Context ctx) {
        var name = ctx.pathParam("name");
        try{
            var movies = movieService.getAllWatchedMovies(name);
            if (movies.isEmpty()) {
                ctx.status(404).result("No movies found");
            }
            ctx.json(movies).status(200);
        } catch (SQLException e) {
            ctx.json(e.getMessage()).status(500);
        }
    }
    public void getUpcomingMovies(Context ctx) {
        try {
            var upComingMovies = movieService.getAllUpcomingMovies();
            ctx.json(upComingMovies).status(200);
        } catch (SQLException e) {
           ctx.json(e.getMessage()).status(500);
        }
    }
    public void save(Context ctx) {
        var movie = ctx.bodyAsClass(MovieRequestBody.class);
        if(movie == null){
            ctx.status(400).result("Invalid movie Data");
            return;
        }

        try {
            movieService.saveMovie(movie);
        } catch (SQLException e) {
            ctx.status(500).result("Internal Server Error");
        }
        ctx.status(200).result("Movie Saved Successfully");
    }
    public void watchMovies(Context ctx) {
        ctx.status(402);
    }
}
