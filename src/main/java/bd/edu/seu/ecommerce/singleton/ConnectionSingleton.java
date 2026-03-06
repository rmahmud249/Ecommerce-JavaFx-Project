package bd.edu.seu.ecommerce.singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionSingleton {
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "raselCSE1234";
    private static final String DB_NAME = "ecommerce";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    private static Connection connection;
    private static ConnectionSingleton instance = new ConnectionSingleton();
    private ConnectionSingleton() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            System.err.println("Failed to connect to database");
            ex.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return connection;
    }

}
