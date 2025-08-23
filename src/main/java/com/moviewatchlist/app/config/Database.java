package com.moviewatchlist.app.config;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    private static DataSource dataSource;

    private Database() {}

    public static synchronized void init(String databasePath) {
        if (dataSource != null) {
            logger.debug("Database already initialized");
            return;
        }

        logger.info("Initializing database at path: {}", databasePath);

        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            config.setBusyTimeout(10_000);
            config.setJournalMode(SQLiteConfig.JournalMode.WAL);

            SQLiteDataSource ds = new SQLiteDataSource(config);
            ds.setUrl("jdbc:sqlite:" + databasePath);

            runMigrations(ds);
            dataSource = ds;

            logger.info("Database initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize database", e);
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    private static void runMigrations(DataSource ds) {
        logger.info("Running database migrations");
        Flyway flyway = Flyway.configure()
                .dataSource(ds)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
        logger.info("Database migrations completed");
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("Database not initialized. Call init() first.");
        }
        return dataSource.getConnection();
    }
}
