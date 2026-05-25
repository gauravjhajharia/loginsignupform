package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DBConnection.java
 * ------------------
 * Connects to MySQL (Aiven cloud or local).
 * AUTO-CREATES the users table on first startup — no manual SQL needed!
 *
 * RENDER ENVIRONMENT VARIABLES to set:
 *   DB_URL      = jdbc:mysql://your-host:port/defaultdb?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true
 *   DB_USERNAME = avnadmin
 *   DB_PASSWORD = your_aiven_password
 */
public class DBConnection {

    // Reads from environment variables (Render) — falls back to local MySQL
    private static final String URL = System.getenv("DB_URL") != null
            ? System.getenv("DB_URL")
            : "jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String USERNAME = System.getenv("DB_USERNAME") != null
            ? System.getenv("DB_USERNAME")
            : "root";

    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null
            ? System.getenv("DB_PASSWORD")
            : "password";

    // Auto-create table on first load — no manual SQL needed!
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 Statement stmt = conn.createStatement()) {

                // Create users table if it does not exist
                stmt.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                    "  id       INT PRIMARY KEY AUTO_INCREMENT, " +
                    "  name     VARCHAR(100) NOT NULL, " +
                    "  email    VARCHAR(100) NOT NULL UNIQUE, " +
                    "  password VARCHAR(100) NOT NULL" +
                    ")"
                );

                // Insert sample users only if table is empty
                stmt.execute(
                    "INSERT IGNORE INTO users (name, email, password) VALUES " +
                    "('Alice Johnson', 'alice@example.com', 'alice123'), " +
                    "('Bob Smith',     'bob@example.com',   'bob123'),   " +
                    "('Charlie Brown', 'charlie@example.com', 'charlie123')"
                );

                System.out.println("[DBConnection] MySQL connected and table ready.");
            }
        } catch (Exception e) {
            System.err.println("[DBConnection] ERROR: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Returns a live JDBC Connection to the MySQL database.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
