package dao;

import db.DBConnection;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO.java
 * -------------
 * DAO = Data Access Object.
 * This is the "bridge" between the Servlet (controller) and the MySQL database.
 *
 * All SQL queries live here — servlets just call these methods.
 * This keeps concerns separated (MVC architecture).
 *
 * Beginner Tip:
 *   - PreparedStatement = safer than Statement; prevents SQL injection attacks
 *   - ResultSet = the rows returned by a SELECT query
 *   - Always close Connection, PreparedStatement, ResultSet after use
 */
public class UserDAO {

    // ─────────────────────────────────────────────────────────
    // 1. REGISTER USER  (INSERT)
    // ─────────────────────────────────────────────────────────

    /**
     * Inserts a new user into the 'users' table.
     *
     * @param user  User object holding name, email, password
     * @return true if insert succeeded, false otherwise
     */
    public boolean registerUser(User user) {
        // SQL with placeholders '?' — filled in safely by PreparedStatement
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        // try-with-resources automatically closes the connection even if an error occurs
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());     // ? #1 = name
            ps.setString(2, user.getEmail());    // ? #2 = email
            ps.setString(3, user.getPassword()); // ? #3 = password

            int rowsAffected = ps.executeUpdate(); // returns number of rows inserted
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // prints stack trace to Tomcat console (for debugging)
            return false;
        }
    }

    // ─────────────────────────────────────────────────────────
    // 2. LOGIN USER  (SELECT by email + password)
    // ─────────────────────────────────────────────────────────

    /**
     * Checks if email + password match a record in the database.
     *
     * @param email    entered email
     * @param password entered password (plain text for this demo)
     * @return User object if login valid, null if not found
     */
    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery(); // runs SELECT and returns rows

            if (rs.next()) { // rs.next() moves cursor to first row; true if row exists
                // Build a User object from the returned row
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // null = login failed (no matching record)
    }

    // ─────────────────────────────────────────────────────────
    // 3. GET ALL USERS  (SELECT all rows)
    // ─────────────────────────────────────────────────────────

    /**
     * Returns a list of ALL users stored in the database.
     * Used by dashboard.jsp to show the user table.
     *
     * @return List<User>
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Loop through every row returned
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                users.add(user); // add each user to the list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // ─────────────────────────────────────────────────────────
    // 4. GET USER BY ID  (SELECT single row)
    // ─────────────────────────────────────────────────────────

    /**
     * Fetches a single user by their primary key (id).
     * Used by edit.jsp to pre-fill the update form.
     *
     * @param id  the user's database id
     * @return User object, or null if not found
     */
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ─────────────────────────────────────────────────────────
    // 5. UPDATE USER  (UPDATE)
    // ─────────────────────────────────────────────────────────

    /**
     * Updates an existing user's name, email, and password.
     *
     * @param user  User object with updated values + original id
     * @return true if update succeeded
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId()); // WHERE clause uses id

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ─────────────────────────────────────────────────────────
    // 6. DELETE USER  (DELETE)
    // ─────────────────────────────────────────────────────────

    /**
     * Deletes a user from the database by id.
     *
     * @param id  user's primary key
     * @return true if deletion succeeded
     */
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ─────────────────────────────────────────────────────────
    // 7. EMAIL EXISTS CHECK  (for duplicate prevention)
    // ─────────────────────────────────────────────────────────

    /**
     * Checks if an email is already registered.
     * Prevents duplicate registrations.
     *
     * @param email  email to check
     * @return true if email already exists
     */
    public boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true = row found = email taken

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
