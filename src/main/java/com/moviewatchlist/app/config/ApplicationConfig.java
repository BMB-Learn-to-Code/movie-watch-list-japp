package com.moviewatchlist.app.config;

import java.util.Optional;

import static java.lang.Integer.parseInt;

public class ApplicationConfig {
    private final int port;
    private final String version;
    private final String env;

    private ApplicationConfig(int port, String env, String version) {
        this.port = port;
        this.env = env;
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

    public static ApplicationConfig builder() {
        int port = parseInt(Optional.ofNullable(
                System.getenv("APP_PORT")
        ).orElse("7070"));
        String env = Optional.ofNullable(System.getenv("ENV:")).orElse("dev");
        String version = Optional.ofNullable(System.getenv("APP_VERSION:")).orElse("1.0.0");
        return new ApplicationConfig(port, env,version);
    }
}
