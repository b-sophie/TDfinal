package tdfinal.Dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tdfinal.DatabaseConnection;
import tdfinal.Exceptions.NbEmprunt;
import tdfinal.Exceptions.StockInsuffisantException;
import tdfinal.base.Emprunt;
import tdfinal.base.Livre;
import tdfinal.base.Utilisateur;

public class EmpruntDAO {
    public int countEmpruntsUtilisateur(Utilisateur user) {
    String sql = "SELECT COUNT(*) FROM emprunt WHERE Utilisateur = ? AND dateRetour IS NULL";

    try {
        PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
        stmt.setInt(1, user.getId());

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0;
}
    public void add(Emprunt emprunt)  throws StockInsuffisantException, NbEmprunt {
        String sql = "INSERT INTO livre (titre, auteur, stock, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?, ?)";
        int nbEmprunts = countEmpruntsUtilisateur(emprunt.getUtilisateur());

    if (nbEmprunts >= 5) {
    throw new NbEmprunt("L'utilisateur a déjà 5 emprunts.");
    }

    Livre livre = emprunt.getLivre();
        if (livre.getStock() <= 0) {
        throw new StockInsuffisantException("Stock insuffisant");
        }

        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);

            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setInt(3, livre.getStock());
            stmt.setDate(4,java.sql.Date.valueOf(emprunt.getDateEmprunt()));
            stmt.setDate(5, emprunt.getDateRetour() == null ? null : 
                           java.sql.Date.valueOf(emprunt.getDateRetour()));


            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
