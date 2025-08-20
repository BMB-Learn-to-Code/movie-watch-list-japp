package com.moviewatchlist.app.repository;

import com.moviewatchlist.app.config.Database;
import com.moviewatchlist.app.domain.Movie;
import com.moviewatchlist.app.domain.MovieRequestBody;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    public MovieRepository() {}

    public List<Movie> findAll() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";

        try (Connection conn = Database.getConnection();
        var stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                Long releaseDate = rs.getLong("release_timestamp");
                movies.add(new Movie(id, title, releaseDate));
            }
            return movies;

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void saveMovie(MovieRequestBody movie) throws SQLException {
        String sql = "INSERT INTO movies (title, release_timestamp) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movie.title());
            stmt.setLong(2, movie.releaseTimestamp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
