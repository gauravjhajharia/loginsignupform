package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * LoginServlet.java
 * ------------------
 * Handles the User Login flow.
 *
 * URL mapping: /login
 *
 * HTTP GET  → shows the login.jsp form
 * HTTP POST → validates credentials, creates session, redirects to dashboard
 *
 * Beginner Tip:
 *   - HttpSession = server-side storage that persists across pages for a user
 *   - session.setAttribute("key", value) = store data in the session
 *   - session.getAttribute("key")        = retrieve data from session
 *   - session.invalidate()               = destroy the session (logout)
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    /**
     * GET /login → show the login form
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    /**
     * POST /login → validate and start session
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Step 1: Read credentials from the form
        String email    = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        // Step 2: Basic empty field check
        if (email.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Email and Password are required!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        // Step 3: Ask the DAO to verify credentials against the database
        User user = userDAO.loginUser(email, password);

        if (user != null) {
            // ✅ Login SUCCESS

            // Create an HttpSession (or get the existing one)
            HttpSession session = req.getSession();

            // Store the logged-in user's email and name in the session
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userName",  user.getName());
            session.setAttribute("userId",    user.getId());

            // Session expires after 30 minutes of inactivity
            session.setMaxInactiveInterval(30 * 60);

            // Redirect to the protected dashboard page
            resp.sendRedirect("dashboard");

        } else {
            // ❌ Login FAILED — wrong email or password
            req.setAttribute("error", "Invalid email or password. Please try again.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
