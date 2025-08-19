-- V2__update_table.sql
-- This is a placeholder for future database schema changes.
-- Drop and recreate tables with correct structure
DROP TABLE IF EXISTS watched;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS movies;

-- Movies Table
CREATE TABLE movies (
    id INTEGER PRIMARY KEY,
    title TEXT NOT NULL,
    release_timestamp REAL
);

-- Users Table
CREATE TABLE users (
    name TEXT PRIMARY KEY
);

-- Watched Table
CREATE TABLE watched (
    user_username TEXT NOT NULL,
    movie_id INTEGER NOT NULL,
    PRIMARY KEY (user_username, movie_id),
    FOREIGN KEY(user_username) REFERENCES users(name),
    FOREIGN KEY(movie_id) REFERENCES movies(id)
);

CREATE INDEX IF NOT EXISTS idx_users_names ON users(name);