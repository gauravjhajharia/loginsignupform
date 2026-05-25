package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection.java
 * ------------------
 * Connects to Oracle MySQL database using JDBC.
 *
 * HOW TO CONFIGURE:
 *   1. Install MySQL from https://dev.mysql.com/downloads/mysql/
 *   2. Start MySQL service
 *   3. Run database_setup.sql to create testdb and users table
 *   4. Update USERNAME and PASSWORD below to match your MySQL credentials
 */
public class DBConnection {

    // ── MySQL connection settings ─────────────────────────────────────────────
    // Change these to match YOUR MySQL installation:

    private static final String URL      = "jdbc:mysql://localhost:3306/testdb"
                                         + "?useSSL=false"
                                         + "&serverTimezone=UTC"
                                         + "&allowPublicKeyRetrieval=true";

    private static final String USERNAME = "root";      // your MySQL username
    private static final String PASSWORD = "password";  // your MySQL password

    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns a live JDBC Connection to the testdb database.
     * Make sure MySQL is running before calling this.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. "
                + "Add mysql-connector-j.jar to WEB-INF/lib", e);
        }
    }
}
