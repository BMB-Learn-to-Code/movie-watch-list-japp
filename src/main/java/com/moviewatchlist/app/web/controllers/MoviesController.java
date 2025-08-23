package com.moviewatchlist.app.web.controllers;

import com.moviewatchlist.app.domain.movie.MovieRequestBody;
import com.moviewatchlist.app.service.MovieService;
import com.moviewatchlist.app.web.ResponseUtil;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class MoviesController {
    private static final Logger logger = LoggerFactory.getLogger(MoviesController.class);
    private final MovieService movieService;

    public MoviesController() {
        this.movieService = new MovieService();
    }

    public void getAll(Context ctx) {
        try {
            var movies = movieService.getAllMovies();
            if (movies.isEmpty()) {
                ctx.status(404).json(ResponseUtil.createErrorResponse("No movies found"));
                return;
            }
            ctx.status(200).json(movies);
        } catch (SQLException e) {
            logger.error("Error fetching all movies", e);
            ctx.status(500).json(ResponseUtil.createErrorResponse("Internal server error"));
        }
    }

    public void getAllWatched(Context ctx) {
        String name = ctx.pathParam("name");
        try {
            var movies = movieService.getAllWatchedMovies(name);
            if (movies.isEmpty()) {
                ctx.status(404).json(ResponseUtil.createErrorResponse("No watched movies found for user: " + name));
                return;
            }
            ctx.status(200).json(movies);
        } catch (SQLException e) {
            logger.error("Error fetching watched movies for user: {}", name, e);
            ctx.status(500).json(ResponseUtil.createErrorResponse("Internal server error"));
        }
    }

    public void getUpcomingMovies(Context ctx) {
        try {
            var upcomingMovies = movieService.getAllUpcomingMovies();
            ctx.status(200).json(upcomingMovies);
        } catch (SQLException e) {
            logger.error("Error fetching upcoming movies", e);
            ctx.status(500).json(ResponseUtil.createErrorResponse("Internal server error"));
        }
    }

    public void save(Context ctx) {
        try {
            var movie = ctx.bodyAsClass(MovieRequestBody.class);
            if (movie == null || movie.title() == null || movie.title().trim().isEmpty()) {
                ctx.status(400).json(ResponseUtil.createErrorResponse("Invalid movie data"));
                return;
            }

            movieService.saveMovie(movie);
            ctx.status(201).json(ResponseUtil.createSuccessResponse("Movie saved successfully"));
        } catch (SQLException e) {
            logger.error("Error saving movie", e);
            ctx.status(500).json(ResponseUtil.createErrorResponse("Internal server error"));
        }
    }

    public void watchMovies(Context ctx) {
        ctx.status(501).json(ResponseUtil.createErrorResponse("Not implemented"));
    }
}
