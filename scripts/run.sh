#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR/.."

: "${ENV:=dev}"
: "${PORT:=8080}"
: "${VERSION:=1.0.0}"
# Check if the wrapper exists if not then use the default mvn command
if [[ -f mvnw ]]; then
  echo "Using mvnw wrapper"
  MVN="./mvnw"
else
  echo "Using mvn command"
  MVN="mvn"
fi

# Check if the JAVA Version is 21 or higher
if [[ "$(java -version 2>&1 | awk -F '"' '/version/ {
    print $2
  }' | cut -d'.' -f1)" -lt 21 ]]; then
  echo "Java version must be 21 or higher. Current version: $(java -version 2>&1 | awk -F '"' '/version/ { print $2 }')"
  exit 1
fi

echo "Starting Movie Watch List App (ENV=$ENV PORT=$PORT VERSION=$VERSION)"
# compile then run
exec $MVN -e -DskipTests clean compile exec:java
