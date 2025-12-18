package com.buydens;

import com.buydens.Library.Book;
import com.buydens.Library.BookDao;
import com.buydens.Library.User;
import com.buydens.Library.UserDao;

import java.util.List;

public class MainController {

    public static void populateBooks() {

        // BookDao.deleteAll();

        // if (!BookDao.findAll().isEmpty()) return;

        BookDao.insert(new Book("The Wise Man's Fear", "Patrick Rothfuss", 3, "data:image/jpeg;base64,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", "The second book in the Kingkiller Chronicle series."));
        // BookDao.insert(new Book("Le Petit Prince", "Antoine de Saint-Exupéry", 5));
        // BookDao.insert(new Book("L'Étranger", "Albert Camus", 2));
        // BookDao.insert(new Book("Harry Potter", "J.K. Rowling", 4));
    }

    public static List<Book> loadBooks() {
        return BookDao.findAll();
    }

    public static int countBooks() {
        return BookDao.findAll().size();
    }

    public static boolean userExists(String pseudo) {
        return UserDao.getUserByPseudo(pseudo) != null;
    }


    public static User createUser(String pseudo, String mdp) {
        User user = new User(0, pseudo, mdp, null);
        UserDao.insert(user);
        return user;
    }

    public static User authenticateUser(String pseudo, String mdp) {
    if (pseudo == null || mdp == null) return null;

    String p = pseudo.trim();
    String m = mdp.trim();
    System.out.println("Attempt login: '" + p + "' / '" + m + "'");

    User user = UserDao.getUserByPseudo(p);
    if (user != null) {
        System.out.println("Found user: " + user.getPseudo() + " / " + user.getMdp());
        if (user.getMdp().equals(m)) {
            System.out.println("Login successful for " + p);
            return user;
        } else {
            System.out.println("Password mismatch");
        }
    } else {
        System.out.println("User not found in DB");
    }
    return null;

}

// public static void createAdminIfNotExists() {
//     if (!userExists("admin")) {
//         User admin = new User(0, "juju", "0000", "ADMIN");
//         UserDao.insert(admin);
//         System.out.println("Compte admin créé");
//     }
// }


    

    /*public static User authenticateUser(String pseudo, String mdp) {
        if ("juju".equals(pseudo) && "0000".equals(mdp)) {
            return new User(1, pseudo, mdp, null);
        }
        return null;
    }*/
}







/*package com.buydens;

import com.buydens.Library.Book;
import com.buydens.Library.BookDao;
import com.buydens.Library.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    public static List<Book> loadBooks() {
        return BookDao.findAll();
    }
    public static int countBooks() {
        return BookDao.findAll().size();
    }
    public static User authenticateUser(String pseudo, String mdp) {
        if ("juju".equals(pseudo) && "0000".equals(mdp)) {
            return new User(2, pseudo, mdp, null);
        
        return null; // À implémenter
    }
}}*/

        

    /*private static List<User> users = new ArrayList<>();

static {
    users.add(new User(1, "thomas", "1234", null));
    users.add(new User(2, "juju", "0000", null));
}


    // Authentifier un utilisateur
    public static User authenticateUser(String pseudo, String mdp) {
        for (User u : users) {
            if (u.getPseudo().equals(pseudo) && u.getMdp().equals(mdp)) {
                return u;
            }
        }
        return null;
    }


    public static void populateBooks() {
        Book[] books = new Book[] {
            new Book("B1", "1984", "George Orwell", 1),
            new Book("B2", "Brave New World", "Aldous Huxley", 2),
            new Book("B3", "Fahrenheit 451", "Ray Bradbury", 1),
            new Book("B4", "To Kill a Mockingbird", "Harper Lee", 3),
            new Book("B5", "The Great Gatsby", "F. Scott Fitzgerald", 4),
            new Book("B6", "Moby Dick", "Herman Melville", 1),
            new Book("B7", "Pride and Prejudice", "Jane Austen", 1),
            new Book("B8", "The Catcher in the Rye", "J.D. Salinger", 2),
            new Book("B9", "The Hobbit", "J.R.R. Tolkien", 4),
            new Book("B10", "Animal Farm", "George Orwell", 1)
        };

        for (Book b : books) {
            b.saveToDatabase();
        }

        System.out.println("10 books added to the database.");
    }

    public static Book[] loadBooksFromDatabase() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT id, title, author, available FROM books";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bookList.add(new Book(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("available")
                ));
            }

            System.out.println("Loaded " + bookList.size() + " books from database.");
            if (!bookList.isEmpty()) {
                StringBuilder names = new StringBuilder();
                for (Book b : bookList) names.append(b.getTitle()).append(", ");
                System.out.println("Titles: " + names.toString());
            }

        } catch (Exception e) {
            System.out.println("Error loading books: " + e.getMessage());
        }

        return bookList.toArray(new Book[0]);
    }

    public static int countBooksInDatabase() {
        String sql = "SELECT COUNT(*) FROM books";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            System.out.println("Error counting books: " + e.getMessage());
        }

        return 0;
    }
}*/


