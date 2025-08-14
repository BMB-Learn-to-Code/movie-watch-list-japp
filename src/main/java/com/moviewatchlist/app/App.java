package com.moviewatchlist.app;

import com.moviewatchlist.app.config.ApplicationConfig;
import com.moviewatchlist.app.web.Routes;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        ApplicationConfig cfg = ApplicationConfig.builder();

        System.out.println("Hello Javalings!");
        Javalin app = Javalin.create();

        Routes.register(app,cfg.getEnv(), cfg.getVersion());

        app.start(cfg.getPort());
        org.slf4j.LoggerFactory.getLogger(App.class).info("App started on port {} in {} environment.", cfg.getPort(), cfg.getEnv());
    }
}