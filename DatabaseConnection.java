package TDfinal;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:bibliotheque.db");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
