package TDfinal.Dao;
import TDfinal.base.Livre;
import TDfinal.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
