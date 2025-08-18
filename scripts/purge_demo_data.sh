#!/bin/bash

# purge_demo_data.sh
# Script to remove demo data from the database for development testing

set -e

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

DB_PATH="data/database.db"
SQL_SCRIPT="scripts/purge_demo_data.sql"

echo -e "${YELLOW}Purging demo data from database...${NC}"

# Check if database exists
if [ ! -f "$DB_PATH" ]; then
    echo -e "${RED}Error: Database file not found at $DB_PATH${NC}"
    echo "No database to purge."
    exit 1
fi

# Check if SQL script exists
if [ ! -f "$SQL_SCRIPT" ]; then
    echo -e "${RED}Error: SQL script not found at $SQL_SCRIPT${NC}"
    exit 1
fi

# Confirm before purging
echo -e "${BLUE}This will remove all demo data including:${NC}"
echo "  • Demo users: alice, bob, charlie, diana, eve"
echo "  • Demo movies and their watch history"
echo ""
read -p "Are you sure you want to proceed? (y/N): " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo -e "${YELLOW}Operation cancelled.${NC}"
    exit 0
fi

# Run the SQL script
if command -v sqlite3 &> /dev/null; then
    sqlite3 "$DB_PATH" < "$SQL_SCRIPT"
    echo -e "${GREEN}✓ Demo data purged successfully!${NC}"
    echo ""
    echo "Your database is now clean of demo data."
    echo "You can repopulate it anytime with: ./scripts/populate_demo_data.sh"
else
    echo -e "${RED}Error: sqlite3 command not found${NC}"
    echo "Please install SQLite3 to run this script."
    exit 1
fi
