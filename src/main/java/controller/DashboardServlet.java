package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * DashboardServlet.java
 * ----------------------
 * Serves the main dashboard page after login.
 *
 * URL mapping: /dashboard
 *
 * Responsibilities:
 *   1. Check if user has an active session (protect the page)
 *   2. Fetch all users from DB
 *   3. Pass user list to dashboard.jsp
 *
 * Beginner Tip:
 *   - This is how you "protect" pages in Servlet-based apps
 *   - If no session → redirect to login (user can't view dashboard without logging in)
 *   - req.setAttribute() passes data to the JSP for display
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    /**
     * GET /dashboard → show the user list (if logged in)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Step 1: Session guard — check if the user is logged in
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            // 🔒 Not logged in → redirect to login
            resp.sendRedirect("login.jsp?error=Please+login+first.");
            return;
        }

        // Step 2: Optional — get search query from URL parameter
        String searchQuery = req.getParameter("search");

        // Step 3: Fetch users from database
        java.util.List<User> users = userDAO.getAllUsers();

        // Step 4: Filter users if search query is present
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            String q = searchQuery.trim().toLowerCase();
            users.removeIf(u ->
                !u.getName().toLowerCase().contains(q) &&
                !u.getEmail().toLowerCase().contains(q)
            );
            req.setAttribute("searchQuery", searchQuery);
        }

        // Step 5: Set user list as a request attribute — accessible in JSP via ${users}
        req.setAttribute("users", users);

        // Step 6: Forward to dashboard JSP for rendering
        req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }
}
