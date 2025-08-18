package com.moviewatchlist.app.web.controllers;

import io.javalin.http.Context;

import java.util.Map;

public class HealthController {
    private final String version;
    private final String env;

    public HealthController(String version, String env) {
        this.version = version;
        this.env = env;
    }

    public String getVersion() {
        return version;
    }

    public String getEnv() {
        return env;
    }

    public void health(Context ctx) {
        ctx.json(Map.of("status", "up", "Env", getEnv(), "version", getVersion()));
    }
}