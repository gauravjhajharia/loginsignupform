package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DBConnection.java
 * ------------------
 * Provides JDBC connections to an H2 embedded database.
 *
 * WHY H2?
 *   H2 is a pure-Java database that runs INSIDE the JVM.
 *   No separate MySQL server needed — zero installation!
 *   Data is saved to a file, so it persists between restarts.
 *
 * TO SWITCH BACK TO MYSQL when MySQL is available:
 *   1. Comment out the H2 block below
 *   2. Uncomment the MySQL block
 *   3. In pom.xml, swap h2 dependency back to mysql-connector-j
 */
public class DBConnection {

    // ─── H2 File-based database (NO MySQL needed) ────────────────────────────
    // DB file saved at: <tomcat-dir>/h2data/logindb
    private static final String URL      = "jdbc:h2:file:./h2data/logindb;AUTO_SERVER=TRUE";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private static final String DRIVER   = "org.h2.Driver";

    // ─── MySQL (uncomment when MySQL is installed) ────────────────────────────
    // private static final String URL      = "jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC";
    // private static final String USERNAME = "root";
    // private static final String PASSWORD = "password";
    // private static final String DRIVER   = "com.mysql.cj.jdbc.Driver";

    // ─── Static initializer: create table if not exists ──────────────────────
    // Runs once when the class is first loaded by Tomcat.
    static {
        try {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 Statement stmt = conn.createStatement()) {

                // Create 'users' table if it does not already exist
                stmt.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                    "  id       INT PRIMARY KEY AUTO_INCREMENT, " +
                    "  name     VARCHAR(100) NOT NULL, " +
                    "  email    VARCHAR(100) NOT NULL UNIQUE, " +
                    "  password VARCHAR(100) NOT NULL" +
                    ")"
                );

                // Insert 3 sample users (only if table is empty)
                stmt.execute(
                    "MERGE INTO users (name, email, password) KEY(email) VALUES " +
                    "('Alice Johnson', 'alice@example.com', 'alice123'), " +
                    "('Bob Smith',     'bob@example.com',   'bob123'),   " +
                    "('Charlie Brown', 'charlie@example.com', 'charlie123')"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("DBConnection init failed: " + e.getMessage());
        }
    }

    /**
     * Returns a live Connection to the database.
     * Call this from UserDAO whenever you need to run a query.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
