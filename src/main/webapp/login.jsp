<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - LoginSignupCRUD</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<nav class="navbar">
    <a href="index.jsp" class="brand" style="color:white; text-decoration:none;">LoginSignupCRUD</a>
    <div>
        <a href="index.jsp">Home</a>
        <a href="register">Register</a>
    </div>
</nav>

<div class="card">
    <h2>Welcome Back</h2>
    <p class="subtitle">Sign in to your account to continue</p>

    <%
        String successMsg = request.getParameter("success");
        if (successMsg != null && !successMsg.isEmpty()) {
    %>
        <div class="alert alert-success">&#10003; <%= successMsg %></div>
    <%  } %>

    <%
        Object errorAttr = request.getAttribute("error");
        if (errorAttr != null) {
    %>
        <div class="alert alert-error">&#9888; <%= errorAttr %></div>
    <%  } else {
            String errorParam = request.getParameter("error");
            if (errorParam != null && !errorParam.isEmpty()) {
    %>
        <div class="alert alert-error">&#9888; <%= errorParam %></div>
    <%      }
        }
    %>

    <form action="login" method="post" id="loginForm" novalidate>

        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email"
                   placeholder="you@example.com" required autocomplete="email" />
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <div class="password-wrapper">
                <input type="password" id="password" name="password"
                       placeholder="Enter your password" required autocomplete="current-password" />
                <button type="button" class="password-toggle" id="togglePassword"
                        title="Show/Hide password">&#128065;</button>
            </div>
        </div>

        <button type="submit" class="btn btn-primary" id="loginSubmitBtn">Sign In</button>
    </form>

    <div class="form-footer">
        Don't have an account? <a href="register">Create one here</a>
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

    document.getElementById('loginForm').addEventListener('submit', function (e) {
        var email = document.getElementById('email').value.trim();
        var pwd   = document.getElementById('password').value.trim();
        if (!email || !pwd) {
            e.preventDefault();
            alert('Please fill in both Email and Password.');
        }
    });
</script>

</body>
</html>
