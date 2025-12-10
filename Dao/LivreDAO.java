package tdfinal.Dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tdfinal.DatabaseConnection;
import tdfinal.base.Livre;

public class LivreDAO {

    public void add(Livre livre) {
        String sql = "INSERT INTO livre (titre, auteur, stock) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);

            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setInt(3, livre.getStock());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public Livre getById(int id) {
        String sql = "SELECT * FROM livre WHERE id = ?";
        Livre livre = null;

        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                livre = new Livre(
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livre;
    }

    // Récupérer tous les livres
    public List<Livre> getAll() {
        String sql = "SELECT * FROM livre";
        List<Livre> livres = new ArrayList<>();

        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                livres.add(new Livre(
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getInt("stock")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livres;
    }

    // Mettre à jour le stock d’un livre
    public void update(Livre livre) {
        String sql = "UPDATE livre SET titre = ?, auteur = ?, stock = ? WHERE id = ?";

        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setInt(3, livre.getStock());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
