package com.moviewatchlist.app.web;

import com.moviewatchlist.app.web.controllers.HealthController;
import com.moviewatchlist.app.web.controllers.MoviesController;
import io.javalin.Javalin;

public class Routes {
    private Routes(){}

    public static void register(Javalin app, String env, String version) {
        var healthCtrl = new HealthController(env, version);
        app.get("/health", healthCtrl::health);

        var moviesCtrl = new MoviesController();
        app.get("/movies", moviesCtrl::getAll);
        app.get("/movies/watched", moviesCtrl::getWatchedMovies);
        app.get("/movies/upcoming", moviesCtrl::getUpcomingMovies);
        app.post("/movies/watch", moviesCtrl::watchMovies);
        app.get("/movies/watch", ctx -> ctx.status(405).result("Method Not Allowed"));
    }
}
