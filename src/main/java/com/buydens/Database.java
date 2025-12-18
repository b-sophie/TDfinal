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
        String createBooks = "CREATE TABLE IF NOT EXISTS books (" +
                "id TEXT PRIMARY KEY, " +
                "title TEXT NOT NULL, " +
                "author TEXT NOT NULL, " +
                "available INTEGER NOT NULL" +
                ");";

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
            stmt.execute(createBooks);
            stmt.execute(createUsers);
            stmt.execute(createEmprunts);
            stmt.execute(createRootAccount);

            // Migration: ensure users table has an 'mdp' column (legacy DBs used 'password')
            try (ResultSet rs = stmt.executeQuery("PRAGMA table_info(users);")) {
                boolean hasMdp = false;
                boolean hasPassword = false;
                while (rs.next()) {
                    String name = rs.getString("name");
                    if ("mdp".equalsIgnoreCase(name)) hasMdp = true;
                    if ("password".equalsIgnoreCase(name)) hasPassword = true;
                }
                if (!hasMdp) {
                    if (hasPassword) {
                        System.out.println("Migrating users table: adding 'mdp' column and copying from 'password'");
                    } else {
                        System.out.println("Adding missing 'mdp' column to users table");
                    }
                    stmt.execute("ALTER TABLE users ADD COLUMN mdp TEXT");
                    if (hasPassword) {
                        try {
                            stmt.execute("UPDATE users SET mdp = password");
                        } catch (SQLException ex) {
                            System.out.println("Migration copy warning: " + ex.getMessage());
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("Migration check error: " + e.getMessage());
            }

            // Migration: ensure users table has a 'role' column (newer versions expect role)
            try (ResultSet rs2 = stmt.executeQuery("PRAGMA table_info(users);")) {
                boolean hasrole = false;
                while (rs2.next()) {
                    String name = rs2.getString("name");
                    if ("role".equalsIgnoreCase(name)) hasrole = true;
                }
                if (!hasrole) {
                    System.out.println("Adding missing 'role' column to users table");
                    try {
                        stmt.execute("ALTER TABLE users ADD COLUMN role TEXT DEFAULT 'User'");
                        stmt.execute("UPDATE users SET role = 'User' WHERE role IS NULL");
                    } catch (SQLException ex) {
                        System.out.println("Migration role error: " + ex.getMessage());
                    }
                }
            } catch (SQLException e) {
                System.out.println("Migration role check error: " + e.getMessage());
            }

            System.out.println("Database initialized.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
