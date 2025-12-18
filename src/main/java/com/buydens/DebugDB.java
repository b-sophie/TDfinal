package com.buydens;

import com.buydens.Library.Book;
import java.util.List;

public class DebugDB {
    public static void main(String[] args) {
        Database.initialize();
        
        // If called with 'reset [value]', perform one-time stock reset and exit
        if (args != null && args.length > 0 && "reset".equalsIgnoreCase(args[0])) {
            int val = 1;
            if (args.length > 1) {
                try { val = Integer.parseInt(args[1]); } catch (Exception ignored) {}
            }
            try {
                java.nio.file.Path db = java.nio.file.Paths.get("library.db");
                if (java.nio.file.Files.exists(db)) {
                    java.nio.file.Files.copy(db, java.nio.file.Paths.get("library.db.bak"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Backup created: library.db.bak");
                }
                try (java.sql.Connection conn = Database.connect();
                     java.sql.PreparedStatement ps = conn.prepareStatement("UPDATE books SET available = ?")) {
                    ps.setInt(1, val);
                    int updated = ps.executeUpdate();
                    System.out.println("Updated " + updated + " rows to available=" + val);
                }
            } catch (Exception e) {
                System.out.println("Error resetting stock: " + e.getMessage());
            }
            return;
        }

        MainController.populateBooks();

        System.out.println("Count: " + MainController.countBooks());

        List<Book> books = MainController.loadBooks();
        for (Book book : books) {
            System.out.println(book.getId() + " - " + book.getTitle() + " (stock:" + book.getStock() + ")");
            System.out.println("\n--- SQLite schema and tables ---");
            try (java.sql.Connection conn = Database.connect();
                 java.sql.Statement stmt = conn.createStatement()) {
                java.sql.ResultSet rsTables = stmt.executeQuery("SELECT name, sql FROM sqlite_master WHERE type='table'");
                while (rsTables.next()) {
                    System.out.println("Table: " + rsTables.getString("name"));
                    System.out.println(rsTables.getString("sql"));
                }

                System.out.println("\nPRAGMA table_info(books):");
                java.sql.ResultSet rsInfo = stmt.executeQuery("PRAGMA table_info(books);");
                while (rsInfo.next()) {
                    System.out.println(rsInfo.getInt("cid") + " | " + rsInfo.getString("name") + " | " + rsInfo.getString("type") + " | notnull=" + rsInfo.getInt("notnull") + " | dflt_value=" + rsInfo.getString("dflt_value") + " | pk=" + rsInfo.getInt("pk"));
                }

                System.out.println("\nFull rows from books:");
                java.sql.ResultSet rsAll = stmt.executeQuery("SELECT * FROM books");
                java.sql.ResultSetMetaData md = rsAll.getMetaData();
                int cols = md.getColumnCount();
                while (rsAll.next()) {
                    StringBuilder row = new StringBuilder();
                    for (int i = 1; i <= cols; i++) {
                        row.append(md.getColumnName(i)).append("=").append(rsAll.getString(i));
                        if (i < cols) row.append(" | ");
                    }
                    System.out.println(row.toString());
                }
                    /*  Try borrowing B1 twice to test stock decrement and blocking
                    System.out.println("\n--- Test emprunt B1 ---");
                    Book[] bs = MainController.loadBooksFromDatabase();
                    Book b1 = null;
                    for (Book b : bs) if (b.getId().equals("B1")) b1 = b;
                    com.buydens.Library.User tester = new com.buydens.Library.User(999, "tester", "x");
                    if (b1 != null) {
                        System.out.println("Before borrow B1 stock=" + b1.getStock());
                        boolean ok1 = com.buydens.ManagerEmprunt.emprunter(b1, tester);
                        System.out.println("Borrow attempt 1: " + ok1);
                        // reload B1 value
                        for (Book b : MainController.loadBooksFromDatabase()) if (b.getId().equals("B1")) b1 = b;
                        System.out.println("After borrow 1 B1 stock=" + b1.getStock());
                        // attempt until stock 0
                        while (b1.getStock() > 0) {
                            boolean ok = com.buydens.ManagerEmprunt.emprunter(b1, tester);
                            System.out.println("Another borrow: " + ok + " -> stock now may change");
                            for (Book b : MainController.loadBooksFromDatabase()) if (b.getId().equals("B1")) b1 = b;
                            System.out.println("Current stock=" + b1.getStock());
                        }
                        boolean okLast = com.buydens.ManagerEmprunt.emprunter(b1, tester);
                        System.out.println("Borrow attempt when stock==0: " + okLast + " (should be false)");
                    }*/
            } catch (Exception e) {
                System.out.println("Error inspecting DB: " + e.getMessage());
            }
        }
    }
}
