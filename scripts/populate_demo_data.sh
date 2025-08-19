#!/bin/bash

# populate_demo_data.sh
# Script to populate the database with demo data for development testing

set -e

DB_PATH="data/database.db"

echo "Adding demo data..."

sqlite3 "$DB_PATH" < scripts/populate_demo_data.sql

echo "✓ Demo data added successfully!"
echo "  • 5 users: alice, bob, charlie, diana, eve"
echo "  • 26 movies with watch history"
