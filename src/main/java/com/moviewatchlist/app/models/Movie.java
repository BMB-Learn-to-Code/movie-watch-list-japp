package com.moviewatchlist.app.models;

public class Movie {
    private final String title;
    private final Long releaseDate;

    public Movie(String title, Long releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public Long getReleaseDate() {
        return releaseDate;
    }
}
