package TDfinal.Dao;
import TDfinal.base.Utilisateur;
import TDfinal.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UtilisateurDAO {

    public void add(Utilisateur utilisateur) {
        String sql = "INSERT INTO livre (pseudo, mdp) VALUES (?, ?)";

        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);

            stmt.setString(1, utilisateur.getPseudo());
            stmt.setString(2, utilisateur.getMdp());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
