#!/bin/bash

# Remove demo data from database

DB_PATH="data/database.db"

echo "Removing demo data..."

sqlite3 "$DB_PATH" < scripts/purge_demo_data.sql

echo "✓ Demo data removed successfully!"
