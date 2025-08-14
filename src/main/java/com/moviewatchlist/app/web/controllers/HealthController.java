package com.moviewatchlist.app.web.controllers;

import io.javalin.http.Context;

import java.util.Map;

public record HealthController(String version, String env) {
    public void health(Context ctx) {
        ctx.json(Map.of("status", "up", "Env", env, "version", version));
    }
}
