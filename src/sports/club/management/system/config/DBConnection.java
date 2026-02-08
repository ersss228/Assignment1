package sports.club.management.system.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    // Use a real database name instead of a Java package/class string
    private static final String URL = "jdbc:postgresql://localhost:5432/Sport";
    private static final String USER = "postgres";
    private static final String PASSWORD = "naz30june";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

