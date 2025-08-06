package com.moviewatchlist.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest{

    @Test
    public void testAppRuns() {
        assertDoesNotThrow(() -> App.main(new String[]{}), "App should run without throwing exceptions");
    }
}