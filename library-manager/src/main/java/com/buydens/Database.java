package com.buydens;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:sqlite:library.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void initialize() {
        String createBooks = "CREATE TABLE IF NOT EXISTS books (" +
                "id TEXT PRIMARY KEY, " +
                "title TEXT NOT NULL, " +
                "author TEXT NOT NULL, " +
                "available INTEGER NOT NULL" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createBooks);

            System.out.println("Database initialized.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
