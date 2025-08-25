package com.moviewatchlist.app;

import com.moviewatchlist.app.config.ApplicationConfig;
import com.moviewatchlist.app.config.Database;
import com.moviewatchlist.app.web.Routes;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static Javalin createApp(ApplicationConfig config) {
        logger.info("Initializing Movie Watch List application");

        Database.init(config.getDatabasePath());

        Javalin app = Javalin.create(javalinConfig ->
            javalinConfig.showJavalinBanner = false
        );

        Routes.register(app, config.getEnvironment(), config.getVersion());

        return app;
    }

    public static void main(String[] args) {
        ApplicationConfig config = ApplicationConfig.fromEnvironment();
        Javalin app = createApp(config);

        app.start(config.getPort());
        logger.info("App started on port {} in {} environment", config.getPort(), config.getEnvironment());
    }
}
