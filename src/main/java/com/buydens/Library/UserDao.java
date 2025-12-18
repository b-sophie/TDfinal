package com.buydens.Library;

import java.sql.*;
import com.buydens.Database;

public class UserDao {

    public static void insert(User user) {
        String pseudo = user.getPseudo() == null ? null : user.getPseudo().trim();
        String mdp = user.getMdp() == null ? null : user.getMdp().trim();
        String role = user.getRole() == null ? "User" : user.getRole().trim();

        try (Connection conn = Database.connect()) {
            boolean hasPassword = false;
            try (ResultSet rs = conn.createStatement().executeQuery("PRAGMA table_info(users);")) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    if ("password".equalsIgnoreCase(name)) {
                        hasPassword = true;
                        break;
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Schema inspect failed: " + ex.getMessage());
            }

            String sql;
            if (hasPassword) {
                // legacy DB expects 'password' column which is NOT NULL
                sql = "INSERT INTO users(pseudo, mdp, password, role) VALUES(?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO users(pseudo, mdp, role) VALUES(?, ?, ?)";
            }

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, pseudo);
                ps.setString(2, mdp);
                if (hasPassword) {
                    // populate legacy 'password' column as well to satisfy NOT NULL
                    ps.setString(3, mdp);
                    ps.setString(4, role);
                } else {
                    ps.setString(3, role);
                }
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }

                System.out.println("User inserted: " + pseudo + " (" + role + ")");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
        }
    }

    public static User getUserByPseudo(String pseudo) {
        if (pseudo == null) return null;

        // (quasi inchangé)
        String sql = "SELECT * FROM users WHERE LOWER(pseudo) = LOWER(?)";

        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pseudo.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("pseudo").trim(),
                        rs.getString("mdp").trim(),
                        rs.getString("role").trim() 
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
        }
        return null;
    }

}


    
