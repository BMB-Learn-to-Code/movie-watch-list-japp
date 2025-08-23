package com.moviewatchlist.app.web.controllers;

import io.javalin.http.Context;

import java.util.Map;

public class HealthController {
    private final String environment;
    private final String version;

    public HealthController(String environment, String version) {
        this.environment = environment;
        this.version = version;
    }

    public void health(Context ctx) {
        ctx.status(200).json(Map.of(
            "status", "up",
            "environment", environment,
            "version", version
        ));
    }
}