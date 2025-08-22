package com.moviewatchlist.app.web;

import com.moviewatchlist.app.web.controllers.HealthController;
import com.moviewatchlist.app.web.controllers.MoviesController;
import com.moviewatchlist.app.web.controllers.UsersController;
import io.javalin.Javalin;
import org.jetbrains.annotations.NotNull;

public class Routes {
    private Routes(){}

    public static void register(@NotNull Javalin app, String env, String version) {
        var healthCtrl = new HealthController(env, version);
        app.get("/health", healthCtrl::health);

        var moviesCtrl = new MoviesController();
        app.get("/movies", moviesCtrl::getAll);
        app.post("/movies", moviesCtrl::save);
        app.get("/movies/{name}/watched", moviesCtrl::getAllWatched);
        app.get("/movies/upcoming", moviesCtrl::getUpcomingMovies);
        app.post("/movies/watch", moviesCtrl::watchMovies);
        app.get("/movies/watch", ctx -> ctx.status(405).result("Method Not Allowed"));

        var userCtrl = new UsersController();
        app.post("/users", userCtrl::Save);
        app.get("/users", userCtrl::findAllUsers);
    }
}
