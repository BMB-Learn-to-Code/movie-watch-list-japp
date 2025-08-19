package com.moviewatchlist.app.config;

import java.util.Optional;

import static java.lang.Integer.parseInt;

public class ApplicationConfig {
    private final int port;
    private final String version;
    private final String env;
    private final String db_path;

    private ApplicationConfig(int port, String env, String version, String db_path) {
        this.port = port;
        this.env = env;
        this.db_path = db_path;
        this.version = version;
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("port must be between 1 and 65535");
        }
    }
    public int getPort() {
        return port;
    }
    public String getVersion() {
        return version;
    }
    public String getEnv() {
        return env;
    }
    public String getDb_path() {return db_path;}

    public static ApplicationConfig builder() {
        int port = parseInt(Optional.ofNullable(
                System.getenv("APP_PORT")
        ).orElse("7070"));
        String env = Optional.ofNullable(System.getenv("ENV:")).orElse("dev");
        String version = Optional.ofNullable(System.getenv("APP_VERSION:")).orElse("1.0.0");
        String db_path = Optional.ofNullable(System.getenv("DB_PATH")).orElse("./data/database.db");
        return new ApplicationConfig(port, env,version, db_path);
    }
}
