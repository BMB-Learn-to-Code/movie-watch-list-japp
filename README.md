# Movie Watch List (Javalin + Java 21)

A minimal RESTful API for a personal movie watch list, built with:
- Java 21
- Javalin 6
- Maven
- SQLite JDBC (driver dependency present – persistence not yet wired)
- Jackson (JSON serialization)

Currently, the app serves an in‑memory list of movies and a few filtered views.

## Quick Start

Prerequisites:
- Java 21+ (`java -version`)
- Maven (or the included wrapper if added later)
- Bash (for the helper script)

Run (simple):
```bash
mvn clean compile exec:java
```
Or with the helper script (includes version + env echo + Java version guard):
```bash
./scripts/run.sh
```

The server starts (by default) on: http://localhost:7070

## Configuration
Configuration is read from environment variables in `ApplicationConfig`:

| Purpose     | Env Var (intended)                                      | Default | Notes                  |
|-------------|---------------------------------------------------------|---------|------------------------|
| HTTP Port   | `APP_PORT`                                              | 7070    | Must be 1–65535        |
| Environment | `ENV` (code currently looks for `ENV:`)                 | dev     | See Known Issues below |
| App Version | `APP_VERSION` (code currently looks for `APP_VERSION:`) | 1.0.0   | See Known Issues below |

Export before running, e.g.:
```bash
export APP_PORT=9090
export ENV=prod
export APP_VERSION=1.2.3
mvn exec:java
```

### Known Issue – Trailing Colons in Variable Names
`ApplicationConfig` currently calls `System.getenv("ENV:")` and `System.getenv("APP_VERSION:")` (with trailing colons). Typical shells will set variables without colons (e.g. `ENV`, `APP_VERSION`). Until corrected, the app will always fall back to defaults for those two values. If you want to patch it, remove the colons in `ApplicationConfig`.

## API Endpoints

The API exposes the following endpoints:

- `GET /health` — Health check for the service
- `GET /movies` — List all movies
- `GET /movies/watched` — List watched movies
- `GET /movies/upcoming` — List upcoming movies
- `POST /movies/watch` — Mark movies as watched (expects JSON body: `{ "movieIds": [1, 2] }`)
- `GET /movies/watch` — Should return 405 Method Not Allowed

## Postman Collection

A Postman collection file is included to help you test the API endpoints easily:

**File:** `MovieWatchList.postman_collection.json`

### How to Use
1. Open Postman.
2. Click **Import** and select the `MovieWatchList.postman_collection.json` file from the project root.
3. The collection will appear with all endpoints organized for easy testing.
4. The collection uses a variable `{{baseUrl}}` (default: `http://localhost:7070`). You can change this in the collection settings if your server runs on a different port.

Each request in the collection matches an API endpoint and includes example payloads for POST requests.

## Testing
Uses JUnit (both legacy `junit:junit` and Jupiter dependency declared — consider consolidating).

Run tests:
```bash
mvn test
```

Current test (`AppTest`) ensures the application boots without throwing.

## Project Structure
```
src/
  main/java/com/moviewatchlist/app/
    App.java                # Javalin bootstrap & routes
    config/ApplicationConfig.java  # Env/config handling
    models/Movie.java       # Movie record
  test/java/.../AppTest.java       # Basic startup test
scripts/run.sh              # Helper run script
pom.xml                     # Maven build (Java 21, exec plugin)
```

## Logging
Uses `slf4j-simple`. Adjust by supplying a `simplelogger.properties` on the classpath if you need custom levels.

## Roadmap Ideas (Not Implemented Yet)
- Replace in-memory lists with SQLite persistence (driver already included)
- Add CRUD endpoints (POST/PUT/DELETE)
- Pagination / filtering / search
- Proper config for ENV & APP_VERSION (fix trailing colon issue)
- Unified test framework (JUnit Jupiter only) + endpoint tests
- Dockerfile for containerized deployment

## Contributing
1. Fork & clone
2. Create a feature branch
3. Write tests for changes
4. Run `mvn test`
5. Open a PR describing motivation & changes

## License
AG PL-3.0 — see [LICENSE](./LICENSE). If you run a modified version as a network service, you must make the source (including modifications) available to users interacting with it.

## Attribution / Notes
Generated endpoints are intentionally minimal for learning purposes. Feel free to evolve this into a fuller watch list service.

---
Happy hacking!
