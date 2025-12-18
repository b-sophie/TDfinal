package com.buydens;

import com.buydens.Library.Book;
import com.buydens.Library.Emprunt;
import com.buydens.Library.User;
import com.buydens.Library.BookDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManagerEmprunt {

    // // Retourne tous les emprunts de la DB
    // public static List<Emprunt> getEmprunts() {
    //     List<Emprunt> emprunts = new ArrayList<>();
    //     String sql = "SELECT e.book_id, e.user_id, e.date_emprunt, u.pseudo, u.mdp, u.master, b.title, b.author, b.available " +
    //                  "FROM emprunts e " +
    //                  "JOIN users u ON e.user_id = u.id " +
    //                  "JOIN books b ON e.book_id = b.id";

    //     try (Connection conn = Database.connect();
    //          Statement stmt = conn.createStatement();
    //          ResultSet rs = stmt.executeQuery(sql)) {

            // while (rs.next()) {
            //     // Book book = new Book(
            //     //         rs.getString("title"),
            //     //         rs.getString("author"),
            //     //         rs.getInt("available")
            //     // );
            //     // User user = new User(
            //     //         rs.getInt("user_id"),
            //     //         rs.getString("pseudo"),
            //     //         rs.getString("mdp"),
            //     //         rs.getString("master")
            //     // );
            //     // LocalDate date = LocalDate.parse(rs.getString("date_emprunt"));
            //     emprunts.add(new Emprunt(book, user, date));
            // }

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return emprunts;
    // }

    // Emprunter un livre
    public static boolean emprunter(Book book, User user) {
        if (book == null || user == null || book.getStock() <= 0) return false;

        // Décrémenter stock
        int newStock = book.getStock() - 1;
        if (!BookDao.updateStock(book.getId(), newStock)) return false;

        // Ajouter emprunt dans DB
        String sql = "INSERT INTO emprunts(user_id, book_id, date_emprunt) VALUES(?,?,?)";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            ps.setString(2, book.getId());
            ps.setString(3, LocalDate.now().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Recharger stock
        Book updatedBook = BookDao.getBookById(book.getId());
        if (updatedBook != null) book.setStock(updatedBook.getStock());

        return true;
    }

    // Rendre un livre
    public static boolean rendre(Book book, User user) {
        if (book == null || user == null) return false;

        // Supprimer emprunt
        String sql = "DELETE FROM emprunts WHERE user_id=? AND book_id=?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            ps.setString(2, book.getId());
            int deleted = ps.executeUpdate();
            if (deleted == 0) return false; // pas emprunt trouvé
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Incrémenter stock
        int newStock = book.getStock() + 1;
        if (!BookDao.updateStock(book.getId(), newStock)) return false;

        // Recharger stock
        Book updatedBook = BookDao.getBookById(book.getId());
        if (updatedBook != null) book.setStock(updatedBook.getStock());

        return true;
    }

    // Compter le nombre d'emprunts d'un utilisateur directement depuis la DB
    public static long countEmpruntsForUser(User user) {
        String sql = "SELECT COUNT(*) FROM emprunts WHERE user_id=?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

