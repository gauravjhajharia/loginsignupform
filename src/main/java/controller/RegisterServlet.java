package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * RegisterServlet.java
 * ---------------------
 * Handles the User Registration (Signup) flow.
 *
 * URL mapping: /register
 *
 * HTTP GET  → shows the register.jsp form
 * HTTP POST → reads form data, saves user to DB, redirects to login
 *
 * Beginner Tip:
 *   - doGet()  is called when a browser loads the URL (typing in address bar)
 *   - doPost() is called when a form is submitted with method="POST"
 *   - sendRedirect() = tells the browser to go to a different page
 *   - forward()     = server-side forwarding (URL stays same in browser)
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO(); // our DAO layer

    /**
     * GET /register  → show the signup form
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Simply forward the request to register.jsp
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    /**
     * POST /register  → process form submission
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Step 1: Read form fields sent by the browser
        String name     = req.getParameter("name").trim();
        String email    = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        // Step 2: Basic validation — check for empty fields
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "All fields are required!");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return; // stop processing
        }

        // Step 3: Check if email is already registered
        if (userDAO.emailExists(email)) {
            req.setAttribute("error", "Email already registered! Please login.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        // Step 4: Create a User object and save to DB
        User user = new User(name, email, password);
        boolean saved = userDAO.registerUser(user);

        if (saved) {
            // Registration success → redirect to login with a success message
            resp.sendRedirect("login.jsp?success=Registered+successfully!+Please+login.");
        } else {
            req.setAttribute("error", "Registration failed. Please try again.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
