-- purge_demo_data.sql
-- Development script to remove demo data from database
-- Run this to clean up demo data when you want a fresh start

-- Remove watch history for demo users
DELETE FROM watched WHERE user_username IN ('alice', 'bob', 'charlie', 'diana', 'eve');

-- Remove demo movies (based on the titles we inserted)
DELETE FROM movies WHERE title IN (
    'The Shawshank Redemption',
    'Pulp Fiction',
    'The Matrix',
    'Fight Club',
    'Inception',
    'Interstellar',
    'Parasite',
    'Avengers: Endgame',
    'Top Gun: Maverick',
    'Everything Everywhere All at Once',
    'Oppenheimer',
    'Barbie',
    'Dune: Part Two',
    'Inside Out 2',
    'Deadpool & Wolverine',
    'Beetlejuice Beetlejuice',
    'Avatar 3',
    'The Batman 2',
    'Blade Runner 2099',
    'Mission: Impossible 8',
    'Spider-Man 4',
    'Fantastic Four',
    'Captain America: Brave New World',
    'Thunderbolts',
    'The Avengers: Secret Wars',
    'Star Wars: New Jedi Order',
    'Fantastic Beasts 4',
    'John Wick 5'
);

-- Remove demo users
DELETE FROM users WHERE name IN ('alice', 'bob', 'charlie', 'diana', 'eve');
