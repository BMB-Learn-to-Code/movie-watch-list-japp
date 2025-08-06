package com.moviewatchlist.app;

import io.javalin.Javalin;

class App {
	public static void main(String[] args) {
        System.out.println("Hello Javalings!");
        Javalin app = Javalin.create()
                .get("/", ctx -> ctx.result("Hello World"))
                .start(8080);
    }
}