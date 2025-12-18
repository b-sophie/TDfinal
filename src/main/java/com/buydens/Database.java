package com.buydens;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;

public class Database {

    private static final String DB_FILE = determineDbFile();
    private static final String URL = "jdbc:sqlite:" + DB_FILE;

    private static String determineDbFile() {
        // Prefer the module-local DB if present (used when running via Maven),
        // otherwise fall back to a project-level DB.
        File candidate = new File("library-manager" + File.separator + "library.db");
        if (candidate.exists()) return candidate.getPath();
        return "library.db";
    }

    public static Connection connect() {
        try {
            System.out.println("Connecting to DB at: " + new File(DB_FILE).getAbsolutePath());
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void initialize() {
        String createBooks = """
            CREATE TABLE IF NOT EXISTS books (
                id TEXT PRIMARY KEY,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                stock INTEGER NOT NULL DEFAULT 0,
                coverUrl TEXT,
                description TEXT
            );
        """;

        String createUsers = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                pseudo TEXT UNIQUE NOT NULL,
                mdp TEXT NOT NULL,
                role TEXT NOT NULL DEFAULT 'User'
            );
        """;

        String deleteAllUsers = """
            DROP TABLE IF EXISTS users;
        """;

        String createRootAccount = """
            INSERT OR IGNORE INTO users (pseudo, mdp, role) VALUES ('juju', '0000', 'ADMIN');
        """;

        String createEmprunts = """
    CREATE TABLE IF NOT EXISTS emprunts (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        user_id INTEGER NOT NULL,
        book_id TEXT NOT NULL,
        date_emprunt TEXT NOT NULL,
        FOREIGN KEY(user_id) REFERENCES users(id),
        FOREIGN KEY(book_id) REFERENCES books(id)
    );
""";


        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            //stmt.execute(deleteAllUsers);
            stmt.execute("DROP TABLE IF EXISTS books");
            stmt.execute(createBooks);
            stmt.execute(createUsers);
            stmt.execute(createEmprunts);
            stmt.execute(createRootAccount);

            System.out.println("Database initialized.");
        } 
        catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

