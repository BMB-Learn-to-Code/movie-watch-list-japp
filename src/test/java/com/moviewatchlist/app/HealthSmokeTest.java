package com.moviewatchlist.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviewatchlist.app.config.ApplicationConfig;
import com.moviewatchlist.app.domain.Movie;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HealthSmokeTest {
    @Test
    void healthEndpointShouldReturn200AndUpStatus(){
        var cfg = ApplicationConfig.builder();
        Javalin app = App.createApp(cfg);
        JavalinTest.test(app, (server, client) ->{
            var response = client.get("/health");
            assertEquals(200, response.code());
            assertNotNull(response.body());
            var body = response.body().string();
            assertTrue(body.contains("up"), "Response body should contain 'up'");
            assertTrue(body.contains("version"), "Response body should contain 'version'");
        });
    }

    @Test
    void movieEndpointShouldReturn200AndMoviesList(){
        var cfg = ApplicationConfig.builder();
        Javalin app = App.createApp(cfg);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/movies");
            assertEquals(200, response.code());
            assertNotNull(response.body());
            var body = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            List<Movie> movies = mapper.readValue(body, mapper.getTypeFactory().constructCollectionType(List.class, Movie.class));
            var first = movies.getFirst();
            assertNotNull(first.title(), "All movies should have a title");
            assertNotNull(first.releaseTimestamp(), "All movies should have a year");
            System.out.println("Response body: " + body);

        });
    }
}
