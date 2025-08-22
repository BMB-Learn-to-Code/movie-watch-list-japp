# Movie Watch List (Javalin + Java 21)

A RESTful API for a personal movie watch list, built with:
- Java 21
- Javalin 6
- Maven
- SQLite JDBC with database persistence
- Jackson (JSON serialization)
- Flyway database migrations

The application provides a full-featured movie catalog with user-specific watch lists, backed by SQLite database for data persistence.

## Quick Start

Prerequisites:
- Java 21+ (`java -version`)
- Maven (or the included wrapper if added later)
- Bash (for the helper script)

Run (simple):
```bash
mvn clean compile exec:java
```
Or with the helper script (includes version/env echo + Java version guard):
```bash
./scripts/run.sh
```

Default base URL: `http://localhost:7070`

The database will be automatically initialized on first run using Flyway migrations.

## Configuration
Configuration is read from environment variables in `ApplicationConfig`:

| Purpose     | Env Var                | Default | Notes                  |
|-------------|------------------------|---------|------------------------|
| HTTP Port   | `APP_PORT`             | 7070    | Must be 1–65535        |
| Environment | `ENV`                  | dev     | Environment identifier |
| App Version | `APP_VERSION`          | 1.0.0   | Application version    |

Example:
```bash
export APP_PORT=9090
export ENV=prod
export APP_VERSION=1.2.3
mvn exec:java
```

## Database Schema

The application uses SQLite with the following tables:
- `movies` - Master catalog of all movies (id, title, release_timestamp)
- `users` - User accounts (name as primary key)
- `watched` - Junction table tracking which users have watched which movies

## API Endpoints
All responses are JSON unless stated otherwise.

### 1. Health Check
`GET /health`

Returns application health status with environment and version information.

Sample response:
```json
{ "status": "up", "Env": "dev", "version": "1.0.0" }
```

### 2. List All Movies
`GET /movies`

Returns all movies in the catalog from the database.

Sample response:
```json
[
  { "id": 1, "title": "Inception", "releaseTimestamp": 1279324800 },
  { "id": 2, "title": "The Matrix", "releaseTimestamp": 922838400 },
  { "id": 3, "title": "Interstellar", "releaseTimestamp": 1415232000 }
]
```

Status codes:
- `200 OK` - Movies found and returned
- `404 Not Found` - No movies in database
- `500 Internal Server Error` - Database error

### 3. Add New Movie
`POST /movies`

Adds a new movie to the catalog.

Request body:
```json
{ "title": "Movie Title", "releaseTimestamp": 1735689600 }
```

Response:
- `200 OK` - Movie saved successfully
- `400 Bad Request` - Invalid movie data
- `500 Internal Server Error` - Database error

### 4. Get Watched Movies for User
`GET /movies/{username}/watched`

Returns all movies that a specific user has marked as watched.

Sample response:
```json
[
  { "id": 1, "title": "Inception", "releaseTimestamp": 1279324800 },
  { "id": 3, "title": "Interstellar", "releaseTimestamp": 1415232000 }
]
```

Status codes:
- `200 OK` - Watched movies found and returned
- `404 Not Found` - No watched movies for user
- `500 Internal Server Error` - Database error

### 5. Get Upcoming Movies
`GET /movies/upcoming`

Returns movies with release timestamps in the future (after current time).

Response format same as "List All Movies" but filtered for future releases.

### 6. Mark Movie as Watched (Not Implemented)
`POST /movies/watch`

Currently returns `402 Payment Required` - functionality not yet implemented.

### 7. Method Not Allowed Guard
`GET /movies/watch` → `405 Method Not Allowed` (explicitly implemented guard).

## Data Model

### Movie Record
```json
{
  "id": 1,
  "title": "Movie Title", 
  "releaseTimestamp": 1735689600
}
```

### Movie Request Body (for POST /movies)
```json
{
  "title": "Movie Title",
  "releaseTimestamp": 1735689600
}
```

Note: `releaseTimestamp` uses Unix timestamp format (seconds since epoch).

## Example cURL Commands
```bash
# Health check
curl -s http://localhost:7070/health | jq

# List all movies
curl -s http://localhost:7070/movies | jq

# Add new movie
curl -i -X POST http://localhost:7070/movies \
  -H 'Content-Type: application/json' \
  -d '{"title":"Dune: Part Three","releaseTimestamp":1767225600}'

# Get watched movies for user "john"
curl -s http://localhost:7070/movies/john/watched | jq

# Get upcoming movies
curl -s http://localhost:7070/movies/upcoming | jq

# Try to mark movie as watched (not implemented)
curl -i -X POST http://localhost:7070/movies/watch

# Try GET on watch endpoint (should return 405)
curl -i http://localhost:7070/movies/watch
```

## Testing Files
- `MovieWatchList.postman_collection.json` - Postman collection with all API endpoints
- `MovieWatchList.http` - HTTP file for IDE REST clients (IntelliJ, VS Code)

Both files use the base URL `http://localhost:7070` and include examples for all implemented endpoints.

## Testing
JUnit tests are available. Run with:
```bash
mvn test
```

Current test coverage includes application bootstrap verification.

## Project Structure
```
src/
  main/java/com/moviewatchlist/app/
    App.java                          # Application bootstrap
    config/
      ApplicationConfig.java          # Environment configuration
      Database.java                   # Database connection management
    domain/
      Movie.java                      # Movie entity record
      MovieRequestBody.java           # Request DTO for movie creation
    repository/
      MovieRepository.java            # Data access layer
    service/
      MovieService.java               # Business logic layer
    web/
      Routes.java                     # Route registration
      controllers/
        HealthController.java         # Health endpoint
        MoviesController.java         # Movie endpoints
  resources/
    db/migration/
      V1__create_tables.sql          # Database schema
scripts/
  *.sh                              # Various utility scripts
```

## Database
- Uses SQLite for persistence
- Flyway for database migrations
- Database file created automatically on first run
- Schema includes movies catalog and user watch tracking

## Logging
Uses `slf4j-simple`. Override defaults via `simplelogger.properties` on classpath if needed.

## Known Limitations
- `POST /movies/watch` endpoint is not implemented (returns 402)
- No user management endpoints (users referenced in watched movies)
- No movie deletion or update endpoints
- No pagination for large movie lists
- No input validation beyond basic null checks

## Roadmap Ideas
- Implement watch movie functionality
- Add user management endpoints
- Add movie CRUD operations (PUT/PATCH/DELETE)
- Input validation with Bean Validation
- Pagination and filtering
- Search functionality
- Authentication and authorization
- API documentation with OpenAPI/Swagger
- Docker containerization
- CI/CD pipeline

## Contributing
1. Fork & clone
2. Branch: `feat/your-feature`
3. Add/adjust tests
4. `mvn test`
5. PR with clear description & rationale

## License
AGPL-3.0 — see [LICENSE](./LICENSE). Running a modified network service requires offering the corresponding source to its users.

---
Happy coding!
