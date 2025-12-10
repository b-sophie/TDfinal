package tdfinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/buyde/Documents/bibliotheque/TDfinal/test.sqlite");

                System.out.println("Database connected: " + connection.getMetaData().getURL());

                // Test simple : vérifier s'il y a des enregistrements dans la table 'livre'
                try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM livre");
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("livres count = " + rs.getInt(1));
                    }
                } catch (SQLException ex) {
                    System.out.println("La table 'livre' est introuvable ou la requête a échoué : " + ex.getMessage());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}

