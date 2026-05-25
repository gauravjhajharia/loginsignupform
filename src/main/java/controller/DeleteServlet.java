package controller;

import dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * DeleteServlet.java
 * -------------------
 * Handles User Deletion.
 *
 * URL mapping: /delete
 *
 * HTTP GET → reads user id from query param, calls DAO to delete, redirects to dashboard
 *
 * Note: In production apps, DELETE operations use HTTP DELETE method.
 * For simplicity with HTML links, we use GET here (common in beginner projects).
 *
 * Beginner Tip:
 *   - A "?id=5" at the end of a URL is called a query parameter
 *   - req.getParameter("id") reads it as a String — convert to int with Integer.parseInt()
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    /**
     * GET /delete?id=5 → delete user with that id
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Session guard — only logged-in users can delete
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            resp.sendRedirect("login.jsp?error=Please+login+first.");
            return;
        }

        // Read the id from URL
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            resp.sendRedirect("dashboard?error=Invalid+request.");
            return;
        }

        int id = Integer.parseInt(idParam);
        boolean deleted = userDAO.deleteUser(id);

        if (deleted) {
            resp.sendRedirect("dashboard?success=User+deleted+successfully.");
        } else {
            resp.sendRedirect("dashboard?error=Delete+failed.+User+not+found.");
        }
    }
}
