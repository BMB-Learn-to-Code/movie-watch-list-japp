#!/bin/bash

# init_db.sh
# Simple script to initialize the database from scratch

set -e

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

DB_PATH="data/database.db"
DATA_DIR="data"

echo -e "${YELLOW}Initializing database...${NC}"

# Create data directory if it doesn't exist
if [ ! -d "$DATA_DIR" ]; then
    mkdir -p "$DATA_DIR"
    echo "Created data directory"
fi

# Remove existing database if it exists
if [ -f "$DB_PATH" ]; then
    echo "Removing existing database..."
    rm "$DB_PATH"
fi

# Build the project to ensure migrations are available
echo "Building project..."
./mvnw clean compile -q

# Run the application briefly to trigger Flyway migrations
echo "Running Flyway migrations..."
./mvnw exec:java -Dexec.mainClass="com.moviewatchlist.app.config.Database" -Dexec.args="$DB_PATH" -q 2>/dev/null || {
    # If the above doesn't work, we'll create the database manually using sqlite3
    if command -v sqlite3 &> /dev/null; then
        echo "Creating database with sqlite3..."
        sqlite3 "$DB_PATH" < src/main/resources/db/migration/V2__update_table.sql
    else
        echo -e "${RED}Error: sqlite3 command not found and Java initialization failed${NC}"
        echo "Please install SQLite3 or fix the Java initialization issue."
        exit 1
    fi
}

echo -e "${GREEN}✓ Database initialized successfully!${NC}"
echo "Database created at: $DB_PATH"
echo ""
echo "To populate with demo data, run:"
echo "  ./scripts/populate_demo_data.sh"
