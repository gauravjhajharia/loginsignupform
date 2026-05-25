<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - LoginSignupCRUD</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<nav class="navbar">
    <a href="index.jsp" class="brand" style="color:white; text-decoration:none;">LoginSignupCRUD</a>
    <div>
        <a href="index.jsp">Home</a>
        <a href="login.jsp">Login</a>
    </div>
</nav>

<div class="card">
    <h2>Create Account</h2>
    <p class="subtitle">Fill in the details below to get started</p>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error">&#9888; <%= request.getAttribute("error") %></div>
    <% } %>

    <form action="register" method="post" id="registerForm" novalidate>

        <div class="form-group">
            <label for="name">Full Name</label>
            <input type="text" id="name" name="name"
                   placeholder="e.g. Alice Johnson" required autocomplete="name"
                   value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>" />
        </div>

        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email"
                   placeholder="you@example.com" required autocomplete="email"
                   value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" />
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <div class="password-wrapper">
                <input type="password" id="password" name="password"
                       placeholder="Create a strong password" required autocomplete="new-password" />
                <button type="button" class="password-toggle" id="togglePassword"
                        title="Show/Hide password">&#128065;</button>
            </div>
        </div>

        <button type="submit" class="btn btn-primary" id="registerSubmitBtn">Create Account</button>
    </form>

    <div class="form-footer">
        Already have an account? <a href="login.jsp">Sign in here</a>
    </div>
</div>

<script>
    var toggleBtn = document.getElementById('togglePassword');
    var passwordInput = document.getElementById('password');

    toggleBtn.addEventListener('click', function () {
        var isHidden = passwordInput.type === 'password';
        passwordInput.type = isHidden ? 'text' : 'password';
        toggleBtn.innerHTML = isHidden ? '&#128065;&#65039;' : '&#128065;';
    });

    document.getElementById('registerForm').addEventListener('submit', function (e) {
        var name  = document.getElementById('name').value.trim();
        var email = document.getElementById('email').value.trim();
        var pwd   = document.getElementById('password').value.trim();

        if (!name || !email || !pwd) {
            e.preventDefault();
            alert('All fields (Name, Email, Password) are required.');
            return;
        }
        if (pwd.length < 4) {
            e.preventDefault();
            alert('Password must be at least 4 characters long.');
        }
    });
</script>

</body>
</html>
