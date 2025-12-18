package com.buydens.Library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.buydens.Database;

public class BookDao {
    
    public static void deleteAll() {
        String sql = "DELETE FROM books";
        try (Connection c = Database.connect();
             Statement s = c.createStatement()) {
            s.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(Book book) {
        String sql = "INSERT OR IGNORE INTO books( title, author, stock) VALUES(?,?,?)";
        //String id = "B" + System.currentTimeMillis();
        try (Connection c = com.buydens.Database.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            /*pstmt.setString(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, stock);*/
            //ps.setString(1, book.getId());
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getStock());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection c = Database.connect();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("stock")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Load books error: " + e.getMessage());
        }

        return books;
    }


     /*public static void updateAvailability(String bookId, int newValue) {
        String sql = "UPDATE books SET available = ? WHERE id = ?";
        try (Connection conn = com.buydens.Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newValue);
            pstmt.setString(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    public static boolean updateStock(String bookId, int stock) {
        String sql = "UPDATE books SET stock = ? WHERE id = ?";
        try (Connection c = Database.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, stock);
            ps.setString(2, bookId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static Book getBookById(String bookId) {
    String sql = "SELECT * FROM books WHERE id=?";
    try (Connection conn = Database.connect();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, bookId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Book(
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("stock")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
}


