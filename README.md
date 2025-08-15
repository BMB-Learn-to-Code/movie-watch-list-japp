# Movie Watch List (Javalin + Java 21)

A minimal RESTful API for a personal movie watch list, built with:
- Java 21
- Javalin 6
- Maven
- SQLite JDBC (driver dependency present – persistence not yet wired)
- Jackson (JSON serialization)

Currently, the app serves an in‑memory list of movies via controller classes. Watched movies start empty (no pre-seeded watched list) and are stored only in memory for the lifetime of the process.

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

## Configuration
Configuration is read from environment variables in `ApplicationConfig`:

| Purpose     | Env Var (intended)                                      | Default | Notes                  |
|-------------|---------------------------------------------------------|---------|------------------------|
| HTTP Port   | `APP_PORT`                                              | 7070    | Must be 1–65535        |
| Environment | `ENV` (code currently looks for `ENV:`)                 | dev     | See Known Issues       |
| App Version | `APP_VERSION` (code currently looks for `APP_VERSION:`) | 1.0.0   | See Known Issues       |

Example:
```bash
export APP_PORT=9090
export ENV=prod
export APP_VERSION=1.2.3
mvn exec:java
```

### Known Issues (Current Code)
| Area               | Issue                                                                                                                                          | Impact                                                                                                | Suggested Fix                                                       |
|--------------------|------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------|
| Env vars           | Uses `System.getenv("ENV:")` & `System.getenv("APP_VERSION:")` (trailing colons)                                                               | Always falls back to defaults unless OS actually defines vars with colons (rare)                      | Remove trailing colons in `ApplicationConfig`                       |
| Health endpoint    | `HealthController` record defined as `(String version, String env)` but instantiated as `new HealthController(env, version)`                   | Returned JSON swaps version/env values (`Env` shows version, `version` shows env)                     | Swap constructor arg order in `Routes` or reorder record components |
| POST /movies/watch | Logic checks existence in master `movies` list, not `watchedMovies`, still returns 409 "Movie already watched" if title exists in base catalog | Can't mark an existing catalog movie as watched; only totally new titles can be added to watched list | Change 409 check to search `watchedMovies` instead                  |
| POST /movies/watch | Success (201) does not echo resource or location header                                                                                        | Harder for clients to confirm what was stored                                                         | Return created movie JSON + maybe `Location` header                 |
| Data persistence   | All data in-memory only                                                                                                                        | Data lost on restart                                                                                  | Add persistence layer (SQLite already available)                    |

## API Endpoints
Implemented in `Routes` + controller classes. All responses JSON unless stated.

### 1. Health
GET `/health`

Current (due to parameter inversion bug) sample response:
```json
{ "status": "up", "Env": "1.0.0", "version": "dev" }
```
Intended (after fix):
```json
{ "status": "up", "Env": "dev", "version": "1.0.0" }
```

### 2. List All Movies
GET `/movies`
Returns static in-memory catalog:
```json
[
  { "title": "Inception", "releaseDate": 2010 },
  { "title": "The Matrix", "releaseDate": 1999 },
  { "title": "Interstellar", "releaseDate": 2014 },
  { "title": "The Shawshank Redemption", "releaseDate": 1994 },
  { "title": "The Godfather", "releaseDate": 1972 },
  { "title": "Dune 3", "releaseDate": 2026 }
]
```

### 3. Watched Movies
GET `/movies/watched`
Returns list of movies the user has marked as watched during this runtime (initially `[]`).

### 4. Upcoming Movies
GET `/movies/upcoming`
Filters catalog by `releaseDate > 2025`.

### 5. Mark a Movie as Watched
POST `/movies/watch`

Request body (movie object – NOT an ID list):
```json
{ "title": "My New Indie", "releaseDate": 2027 }
```
Responses:
- `201 Created` (body currently empty) when accepted
- `400 Bad Request` if any of `title` or `releaseDate` is null / missing
- `409 Conflict` if a movie with the same title already exists in the base catalog (note: this is a logic bug; it should check watched list)

### 6. Method Not Allowed Guard
GET `/movies/watch` → `405 Method Not Allowed` (explicitly implemented guard).

### Model
`Movie` record:
```json
{ "title": "Inception", "releaseDate": 2010 }
```

## Example cURL Commands
```bash
# Health (current buggy env/version order)
curl -s http://localhost:7070/health | jq

# List movies
curl -s http://localhost:7070/movies | jq

# Add watched movie (must be a NEW title not in base list due to current 409 logic)
curl -i -X POST http://localhost:7070/movies/watch \
  -H 'Content-Type: application/json' \
  -d '{"title":"Completely Original","releaseDate":2030}'

# List watched
curl -s http://localhost:7070/movies/watched | jq
```

## Postman & HTTP Files
- `MovieWatchList.postman_collection.json` (import into Postman; uses `{{baseUrl}}` defaulting to `http://localhost:7070`).
- `MovieWatchList.http` (for IDEs like IntelliJ / VS Code REST client).

## Testing
JUnit (both legacy `junit:junit` and Jupiter present — consider consolidating to JUnit 5 only).

Run:
```bash
mvn test
```
Current test: `AppTest` ensures the application boots without throwing.

## Project Structure
```
src/
  main/java/com/moviewatchlist/app/
    App.java                       # Bootstrap & server start
    config/ApplicationConfig.java  # Env/config handling (env var colon issue)
    models/Movie.java              # Movie record
    web/Routes.java                # Route registration
    web/controllers/
      HealthController.java        # /health endpoint
      MoviesController.java        # /movies* endpoints & in-memory state
scripts/
  run.sh                          # Run with Java version guard
  test.sh                         # (if present) convenience test script
  package.sh                      # (if present) packaging script
```

## Logging
Uses `slf4j-simple`. Override defaults via `simplelogger.properties` on classpath if needed.

## Roadmap Ideas
- Fix environment/version retrieval & health parameter inversion
- Correct watched movie duplication logic & return created resource
- Real persistence with SQLite (driver already present)
- CRUD (PUT/PATCH/DELETE) & validation (e.g., Bean Validation)
- Pagination / filtering / search
- Dedicated error handling middleware
- Replace legacy JUnit 4 dependency with pure JUnit 5
- Dockerfile + CI pipeline

## Contributing
1. Fork & clone
2. Branch: `feat/your-feature`
3. Add/adjust tests
4. `mvn test`
5. PR with clear description & rationale

## License
AGPL-3.0 — see [LICENSE](./LICENSE). Running a modified network service requires offering the corresponding source to its users.

## Attribution / Notes
Endpoints are intentionally minimal; feel free to evolve into a fuller watch list service. Known issues are documented above for quick iteration.

---
Happy hacking!
