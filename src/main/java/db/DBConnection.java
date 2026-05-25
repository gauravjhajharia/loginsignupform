package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection.java
 * ------------------
 * Reads DB credentials from ENVIRONMENT VARIABLES so the app works
 * on any cloud platform (Render, Railway, etc.) without code changes.
 *
 * LOCAL DEVELOPMENT:
 *   Set these environment variables in your OS, or just change the
 *   fallback values (the second argument in System.getenv(..., "fallback"))
 *
 * RENDER DEPLOYMENT:
 *   Set these in Render Dashboard → Environment Variables:
 *     DB_URL      = jdbc:mysql://your-host:3306/your-db?useSSL=true&serverTimezone=UTC
 *     DB_USERNAME = your_mysql_username
 *     DB_PASSWORD = your_mysql_password
 */
public class DBConnection {

    // Read from environment variables — fall back to local defaults
    private static final String URL = System.getenv("DB_URL") != null
            ? System.getenv("DB_URL")
            : "jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String USERNAME = System.getenv("DB_USERNAME") != null
            ? System.getenv("DB_USERNAME")
            : "root";

    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null
            ? System.getenv("DB_PASSWORD")
            : "password";   // ← change this for local development

    /**
     * Returns a JDBC Connection to the MySQL database.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }
}
