package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * LogoutServlet.java
 * -------------------
 * Handles User Logout.
 *
 * URL mapping: /logout
 *
 * Steps:
 *   1. Get the current session (without creating a new one)
 *   2. Invalidate (destroy) the session — removes all stored attributes
 *   3. Redirect back to the login page
 *
 * Beginner Tip:
 *   - req.getSession(false) = gets existing session; returns null if none
 *   - req.getSession(true)  = gets existing session OR creates a new one
 *   - session.invalidate()  = completely destroys the session object
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * GET /logout → destroy session and redirect to login
     * (Logout is triggered via a link/button, which sends a GET request)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Get existing session — false means "don't create a new one"
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate(); // 🔒 Destroy the session
        }

        // Redirect to login page with a goodbye message
        resp.sendRedirect("login.jsp?success=You+have+been+logged+out+successfully.");
    }
}
