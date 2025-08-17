package com.moviewatchlist.app.config;

import org.flywaydb.core.Flyway;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static DataSource datasource;


    private Database() {
    }

    public static synchronized void init(String dbPath) {
        if (datasource != null) {
            System.out.println("DB already initialized");
            return;
        }
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        sqLiteConfig.enforceForeignKeys(true);
        sqLiteConfig.setBusyTimeout(10_000);
        sqLiteConfig.setJournalMode(SQLiteConfig.JournalMode.WAL);

        SQLiteDataSource ds = new SQLiteDataSource(sqLiteConfig);
        ds.setUrl("jdbc:sqlite:" + dbPath);

        Flyway flyway = Flyway.configure()
                .dataSource(ds)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();

        datasource = ds;
    }

    public static Connection getConnection() throws SQLException {
        if (datasource == null) {
            throw new IllegalStateException("Database not initialized. Call init() first.");
        }
        return datasource.getConnection();
    }
}
