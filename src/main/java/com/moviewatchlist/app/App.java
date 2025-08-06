package com.moviewatchlist.app;

import com.moviewatchlist.app.models.Movie;
import io.javalin.Javalin;

class App {
	public static void main(String[] args) {
        System.out.println("Hello Javalings!");
        Javalin app = Javalin.create();

        app.get("/movies", ctx -> ctx.json(
                new Movie[]{
                        new Movie("Inception", 2010L),
                        new Movie("The Matrix", 1999L),
                        new Movie("Interstellar", 2014L),
                        new Movie("The Shawshank Redemption", 1994L),
                        new Movie("The Godfather", 1972L)
                }
        ));


        app.start(8080);
    }
}