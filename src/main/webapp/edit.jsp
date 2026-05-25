<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User - LoginSignupCRUD</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<nav class="navbar">
    <a href="dashboard" class="brand" style="color:white; text-decoration:none;">LoginSignupCRUD</a>
    <div>
        <a href="dashboard">Back to Dashboard</a>
        <a href="logout" class="btn btn-logout btn" style="margin-left:10px;">Logout</a>
    </div>
</nav>

<%
    User user = (User) request.getAttribute("user");
%>

<% if (user == null) { %>
    <div class="card">
        <div class="alert alert-error">User not found. <a href="dashboard">Go back to Dashboard</a>.</div>
    </div>
<% } else { %>

<div class="card">
    <h2>Edit User</h2>
    <p class="subtitle">Modify the details and save changes</p>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error">&#9888; <%= request.getAttribute("error") %></div>
    <% } %>

    <form action="update" method="post" id="editForm" novalidate>

        <input type="hidden" name="id" value="<%= user.getId() %>" />

        <div class="form-group">
            <label for="name">Full Name</label>
            <input type="text" id="name" name="name"
                   value="<%= user.getName() %>" required placeholder="Enter full name" />
        </div>

        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email"
                   value="<%= user.getEmail() %>" required placeholder="Enter email" />
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <div class="password-wrapper">
                <input type="password" id="password" name="password"
                       value="<%= user.getPassword() %>" required
                       placeholder="Enter new password" autocomplete="new-password" />
                <button type="button" class="password-toggle" id="togglePassword"
                        title="Show/Hide password">&#128065;</button>
            </div>
        </div>

        <div style="display:flex; gap:12px; margin-top:8px;">
            <button type="submit" class="btn btn-primary" id="updateSubmitBtn" style="flex:1;">
                Save Changes
            </button>
            <a href="dashboard" class="btn btn-outline" style="flex:0.5; text-align:center; padding:11px 0;">
                Cancel
            </a>
        </div>

    </form>
</div>

<% } %>

<script>
    var toggleBtn = document.getElementById('togglePassword');
    var passwordInput = document.getElementById('password');

    if (toggleBtn && passwordInput) {
        toggleBtn.addEventListener('click', function () {
            var isHidden = passwordInput.type === 'password';
            passwordInput.type = isHidden ? 'text' : 'password';
            toggleBtn.innerHTML = isHidden ? '&#128065;&#65039;' : '&#128065;';
        });
    }

    var form = document.getElementById('editForm');
    if (form) {
        form.addEventListener('submit', function (e) {
            var name  = document.getElementById('name').value.trim();
            var email = document.getElementById('email').value.trim();
            var pwd   = document.getElementById('password').value.trim();
            if (!name || !email || !pwd) {
                e.preventDefault();
                alert('All fields are required.');
            }
        });
    }
</script>

</body>
</html>
