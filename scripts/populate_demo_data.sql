-- populate_demo_data.sql
-- Development script to populate database with demo data
-- Run this manually when you want to test the app with dummy data

-- Clear existing data (optional - uncomment if you want to reset)
-- DELETE FROM watched;
-- DELETE FROM movies;
-- DELETE FROM users;

-- Insert demo users
INSERT OR IGNORE INTO users (name) VALUES
    ('alice'),
    ('bob'),
    ('charlie'),
    ('diana'),
    ('eve');

-- Insert movies (mix of past, recent, and upcoming releases)
-- Using Unix timestamps for release_timestamp
INSERT OR IGNORE INTO movies (title, release_timestamp) VALUES
    -- Past/Classic Movies
    ('The Shawshank Redemption', 780019200),  -- 1994-09-23
    ('Pulp Fiction', 782006400),              -- 1994-10-14
    ('The Matrix', 922838400),                -- 1999-03-31
    ('Fight Club', 939859200),                -- 1999-10-15
    ('Inception', 1279324800),                -- 2010-07-16
    ('Interstellar', 1415145600),             -- 2014-11-05
    ('Parasite', 1571875200),                 -- 2019-10-24
    ('Avengers: Endgame', 1556150400),        -- 2019-04-25
    ('Top Gun: Maverick', 1653350400),        -- 2022-05-24
    ('Everything Everywhere All at Once', 1648080000), -- 2022-03-24

    -- Recent releases (2023-2024)
    ('Oppenheimer', 1690243200),              -- 2023-07-25
    ('Barbie', 1690243200),                   -- 2023-07-25
    ('Dune: Part Two', 1709251200),           -- 2024-03-01
    ('Inside Out 2', 1718323200),             -- 2024-06-14
    ('Deadpool & Wolverine', 1721779200),     -- 2024-07-24
    ('Beetlejuice Beetlejuice', 1725580800),  -- 2024-09-06

    -- Upcoming movies (2025-2027)
    ('Avatar 3', 1767225600),                 -- 2025-12-19
    ('The Batman 2', 1791043200),             -- 2026-10-02
    ('Blade Runner 2099', 1743465600),        -- 2025-04-11
    ('Mission: Impossible 8', 1735689600),    -- 2025-01-01
    ('Spider-Man 4', 1751328000),             -- 2025-07-12
    ('Fantastic Four', 1722384000),           -- 2025-07-30
    ('Captain America: Brave New World', 1739577600), -- 2025-02-14
    ('Thunderbolts', 1741622400),             -- 2025-05-02
    ('The Avengers: Secret Wars', 1767225600), -- 2025-12-19
    ('Star Wars: New Jedi Order', 1798761600), -- 2027-01-01
    ('Fantastic Beasts 4', 1756684800),       -- 2025-09-01
    ('John Wick 5', 1767225600);              -- 2025-12-19

-- Insert watch history (some users have watched some movies)
-- Note: Using OR IGNORE to prevent duplicate entries
INSERT OR IGNORE INTO watched (user_username, movie_id) VALUES
    -- Alice is a movie enthusiast
    ('alice', 1),   -- The Shawshank Redemption
    ('alice', 2),   -- Pulp Fiction
    ('alice', 3),   -- The Matrix
    ('alice', 5),   -- Inception
    ('alice', 6),   -- Interstellar
    ('alice', 11),  -- Oppenheimer
    ('alice', 12),  -- Barbie
    ('alice', 13),  -- Dune: Part Two

    -- Bob likes action movies
    ('bob', 3),     -- The Matrix
    ('bob', 4),     -- Fight Club
    ('bob', 8),     -- Avengers: Endgame
    ('bob', 9),     -- Top Gun: Maverick
    ('bob', 15),    -- Deadpool & Wolverine

    -- Charlie prefers newer releases
    ('charlie', 10), -- Everything Everywhere All at Once
    ('charlie', 11), -- Oppenheimer
    ('charlie', 13), -- Dune: Part Two
    ('charlie', 14), -- Inside Out 2
    ('charlie', 16), -- Beetlejuice Beetlejuice

    -- Diana has watched a few classics
    ('diana', 1),    -- The Shawshank Redemption
    ('diana', 5),    -- Inception
    ('diana', 7),    -- Parasite
    ('diana', 12),   -- Barbie

    -- Eve is just getting started
    ('eve', 14),     -- Inside Out 2
    ('eve', 15);     -- Deadpool & Wolverine
