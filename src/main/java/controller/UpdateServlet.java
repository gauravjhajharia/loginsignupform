package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * UpdateServlet.java
 * -------------------
 * Handles User Update (Edit) flow.
 *
 * URL mapping: /update
 *
 * HTTP GET  → fetch user by id and show edit.jsp with pre-filled data
 * HTTP POST → read updated form data, call DAO to update, redirect to dashboard
 *
 * Beginner Tip:
 *   - req.getParameter("id") returns a String — parse to int with Integer.parseInt()
 *   - Always validate session before serving protected operations
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    /**
     * GET /update?id=3 → fetch user and show edit form
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Session guard
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            resp.sendRedirect("login.jsp?error=Please+login+first.");
            return;
        }

        // Read the user ID from the URL query string (?id=...)
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            resp.sendRedirect("dashboard");
            return;
        }

        int id = Integer.parseInt(idParam);
        User user = userDAO.getUserById(id);

        if (user == null) {
            resp.sendRedirect("dashboard?error=User+not+found.");
            return;
        }

        // Pass user object to edit.jsp
        req.setAttribute("user", user);
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    /**
     * POST /update → save updated data to DB
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Session guard
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            resp.sendRedirect("login.jsp?error=Please+login+first.");
            return;
        }

        // Read form fields
        int    id       = Integer.parseInt(req.getParameter("id"));
        String name     = req.getParameter("name").trim();
        String email    = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        // Basic validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "All fields are required!");
            User user = userDAO.getUserById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/edit.jsp").forward(req, resp);
            return;
        }

        // Build updated User object
        User updatedUser = new User(id, name, email, password);
        boolean updated = userDAO.updateUser(updatedUser);

        if (updated) {
            resp.sendRedirect("dashboard?success=User+updated+successfully.");
        } else {
            resp.sendRedirect("dashboard?error=Update+failed.+Please+try+again.");
        }
    }
}
