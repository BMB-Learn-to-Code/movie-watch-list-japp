package com.moviewatchlist.app;

import com.moviewatchlist.app.config.ApplicationConfig;
import com.moviewatchlist.app.models.Movie;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.List;

class App {

    public static void main(String[] args) {
        ApplicationConfig cfg = ApplicationConfig.builder();

        System.out.println("Hello Javalings!");
        Javalin app = Javalin.create();

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Inception", 2010L));
        movies.add(new Movie("The Matrix", 1999L));
        movies.add(new Movie("Interstellar", 2014L));
        movies.add(new Movie("The Shawshank Redemption", 1994L));
        movies.add(new Movie("The Godfather", 1972L));
        movies.add(new Movie("Dune 3", 2026L));

        ArrayList<Movie> watchedMovies = new ArrayList<>(
                List.of(movies.get(0), movies.get(1))
        );

        app.get("/health", ctx -> ctx.result( "{\"status\": \"UP\", \"version\": " + cfg.getVersion() + "\"}").status(200));
        app.get("/movies", ctx -> ctx.json(movies));
        app.get("/movies/watched", ctx -> ctx.json(watchedMovies));
        app.get("/movies/upcoming", ctx -> ctx.json(movies.stream().filter(movie -> movie.releaseDate() > 2025L).toList()));


        app.start(cfg.getPort());
        org.slf4j.LoggerFactory.getLogger(App.class).info("App started on port {} in {} environment.", cfg.getPort(), cfg.getEnv());
    }
}