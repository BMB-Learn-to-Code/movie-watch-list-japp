-- v1__create_tables.sql

-- Movies Table
CREATE TABLE IF NOT EXISTS movies (
    id INTEGER PRIMARY KEY,
    title TEXT NOT NULL,
    release_timestamp REAL
);

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    name TEXT PRIMARY KEY
);

-- Watched Table
CREATE TABLE IF NOT EXISTS watched (
    user_username TEXT NOT NULL,
    movie_id INTEGER NOT NULL,
    PRIMARY KEY (user_username, movie_id),
    FOREIGN KEY(user_username) REFERENCES users(name),
    FOREIGN KEY(movie_id) REFERENCES movies(id)
);

CREATE INDEX IF NOT EXISTS idx_users_names ON users(name);