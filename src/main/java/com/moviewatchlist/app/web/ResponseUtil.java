package com.moviewatchlist.app.web;

import java.util.Map;

public class ResponseUtil {
    private ResponseUtil() {}

    public static Map<String, String> createErrorResponse(String message) {
        return Map.of("error", message);
    }

    public static Map<String, String> createSuccessResponse(String message) {
        return Map.of("message", message);
    }
}
