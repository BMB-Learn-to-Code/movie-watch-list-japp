-- v1__create__tables.sql

BEGIN;

-- Movies Table
CREATE TABLE IF NOT EXISTS movies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT,
    release_timestamp REAL
);

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    name TEXT PRIMARY KEY
);

-- Watched Table
CREATE TABLE IF NOT EXISTS watched (
    user_username TEXT,
    movie_id INTEGER,
    FOREIGN KEY(user_username) REFERENCES users(name),
    FOREIGN KEY(movie_id) REFERENCES movies(id)
);

CREATE INDEX IF NOT EXISTS idx_users_names ON users(name);


COMMIT;

