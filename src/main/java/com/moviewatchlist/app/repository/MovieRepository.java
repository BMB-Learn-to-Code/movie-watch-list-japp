package com.moviewatchlist.app.repository;

import com.moviewatchlist.app.config.Database;
import com.moviewatchlist.app.domain.movie.Movie;
import com.moviewatchlist.app.domain.movie.MovieRequestBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    public List<Movie> findAll() throws SQLException {
        String sql = "SELECT * FROM movies";
        return executeMovieQuery(sql);
    }

    public void saveMovie(MovieRequestBody movie) throws SQLException {
        String sql = "INSERT INTO movies (title, release_timestamp) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movie.title());
            stmt.setLong(2, movie.releaseTimestamp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to save movie: " + movie.title(), e);
        }
    }

    public List<Movie> findAllWatchedMovies(String username) throws SQLException {
        String sql = """
                SELECT movies.id, movies.title, movies.release_timestamp 
                FROM movies 
                JOIN watched ON movies.id = watched.movie_id 
                WHERE user_username = ?
                """;
        return executeMovieQueryWithParam(sql, username);
    }

    public List<Movie> findAllUpcomingMovies() throws SQLException {
        String sql = "SELECT * FROM movies WHERE release_timestamp > ?";
        long currentTimestamp = System.currentTimeMillis() / 1000; // Convert to seconds
        return executeMovieQueryWithParam(sql, currentTimestamp);
    }

    private List<Movie> executeMovieQuery(String sql) throws SQLException {
        try (Connection conn = Database.getConnection();
             var stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return mapResultSetToMovies(rs);
        } catch (SQLException e) {
            throw new SQLException("Failed to execute movie query", e);
        }
    }

    private List<Movie> executeMovieQueryWithParam(String sql, Object param) throws SQLException {
        try (Connection conn = Database.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            if (param instanceof String) {
                stmt.setString(1, (String) param);
            } else if (param instanceof Long) {
                stmt.setLong(1, (Long) param);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                return mapResultSetToMovies(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to execute movie query with parameter", e);
        }
    }

    private List<Movie> mapResultSetToMovies(ResultSet rs) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        while (rs.next()) {
            Long id = rs.getLong("id");
            String title = rs.getString("title");
            Long releaseTimestamp = rs.getLong("release_timestamp");
            movies.add(new Movie(id, title, releaseTimestamp));
        }
        return movies;
    }
}
