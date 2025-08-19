#!/bin/bash

# Initialize database from scratch

DB_PATH="data/database.db"

echo "Initializing database..."

# Create data directory
mkdir -p data

# Remove old database
rm -f "$DB_PATH"

# Create new database
sqlite3 "$DB_PATH" < src/main/resources/db/migration/V2__update_table.sql

echo "✓ Database created at $DB_PATH"
echo "Run ./scripts/populate_demo_data.sh to add test data"
