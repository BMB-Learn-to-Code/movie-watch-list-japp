package com.moviewatchlist.app.config;

import java.util.Optional;

import static java.lang.Integer.parseInt;

public class ApplicationConfig {
    private final int port;
    private final String version;
    private final String environment;
    private final String databasePath;

    private ApplicationConfig(int port, String environment, String version, String databasePath) {
        validatePort(port);
        this.port = port;
        this.environment = environment;
        this.databasePath = databasePath;
        this.version = version;
    }

    private void validatePort(int port) {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port must be between 1 and 65535, got: " + port);
        }
    }

    public int getPort() {
        return port;
    }

    public String getVersion() {
        return version;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public static ApplicationConfig fromEnvironment() {
        int port = parseInt(getEnvOrDefault("APP_PORT", "7070"));
        String environment = getEnvOrDefault("APP_ENV", "dev");
        String version = getEnvOrDefault("APP_VERSION", "1.0.0");
        String databasePath = getEnvOrDefault("DB_PATH", "./data/database.db");

        return new ApplicationConfig(port, environment, version, databasePath);
    }

    private static String getEnvOrDefault(String key, String defaultValue) {
        return Optional.ofNullable(System.getenv(key)).orElse(defaultValue);
    }
}
