package com.moviewatchlist.app.web.controllers;

import io.javalin.http.Context;

import java.util.Map;

public final class HealthController {
    private final String version;
    private final String env;

    public HealthController(String version, String env) {
        this.version = version;
        this.env = env;
    }

    public void health(Context ctx) {
        ctx.json(Map.of("status", "up", "Env", env, "version", version));
    }
}