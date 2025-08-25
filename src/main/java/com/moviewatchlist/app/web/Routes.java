package com.moviewatchlist.app.web;

import com.moviewatchlist.app.web.controllers.HealthController;
import com.moviewatchlist.app.web.controllers.MoviesController;
import com.moviewatchlist.app.web.controllers.UsersController;
import io.javalin.Javalin;
import org.jetbrains.annotations.NotNull;

public class Routes {
    private Routes() {}

    public static void register(@NotNull Javalin app, String environment, String version) {
        registerHealthRoutes(app, environment, version);
        registerMovieRoutes(app);
        registerUserRoutes(app);
    }

    private static void registerHealthRoutes(Javalin app, String environment, String version) {
        HealthController healthController = new HealthController(environment, version);
        app.get("/health", healthController::health);
    }

    private static void registerMovieRoutes(Javalin app) {
        MoviesController moviesController = new MoviesController();
        app.get("/movies", moviesController::getAll);
        app.post("/movies", moviesController::save);
        app.get("/movies/{name}/watched", moviesController::getAllWatched);
        app.get("/movies/upcoming", moviesController::getUpcomingMovies);
        app.post("/movies/watch", moviesController::watchMovie);
    }

    private static void registerUserRoutes(Javalin app) {
        UsersController usersController = new UsersController();
        app.post("/users", usersController::save);
        app.get("/users", usersController::findAllUsers);
    }
}
