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

        BookDao.insert(new Book("The Wise Man's Fear", "Patrick Rothfuss", 3, "https://covers.openlibrary.org/b/id/8231996-L.jpg", "The second book in the Kingkiller Chronicle series."));
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


