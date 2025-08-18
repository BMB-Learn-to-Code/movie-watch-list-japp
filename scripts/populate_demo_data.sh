#!/bin/bash

# populate_demo_data.sh
# Script to populate the database with demo data for development testing

set -e

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

DB_PATH="data/database.db"
SQL_SCRIPT="scripts/populate_demo_data.sql"

echo -e "${YELLOW}Populating database with demo data...${NC}"

# Check if database exists
if [ ! -f "$DB_PATH" ]; then
    echo -e "${RED}Error: Database file not found at $DB_PATH${NC}"
    echo "Please run the application first to create the database."
    exit 1
fi

# Check if SQL script exists
if [ ! -f "$SQL_SCRIPT" ]; then
    echo -e "${RED}Error: SQL script not found at $SQL_SCRIPT${NC}"
    exit 1
fi

# Run the SQL script
if command -v sqlite3 &> /dev/null; then
    sqlite3 "$DB_PATH" < "$SQL_SCRIPT"
    echo -e "${GREEN}✓ Demo data populated successfully!${NC}"
    echo ""
    echo "Demo data includes:"
    echo "  • 5 users: alice, bob, charlie, diana, eve"
    echo "  • 26 movies: classics, recent releases, and upcoming films"
    echo "  • Watch history for realistic testing scenarios"
    echo ""
    echo "You can now test your application with this demo data."
else
    echo -e "${RED}Error: sqlite3 command not found${NC}"
    echo "Please install SQLite3 to run this script."
    exit 1
fi
