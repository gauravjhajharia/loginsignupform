<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - LoginSignupCRUD</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<nav class="navbar">
    <span class="brand">LoginSignupCRUD</span>
    <div>
        <a href="logout" id="logoutLink" class="btn-logout btn" style="margin-left:10px;">Logout</a>
    </div>
</nav>

<%
    String userName  = (String) session.getAttribute("userName");
    String userEmail = (String) session.getAttribute("userEmail");
%>

<div class="welcome-banner">
    <div>
        <h2>Welcome back, <%= userName != null ? userName : "User" %>!</h2>
        <p>Logged in as: <strong><%= userEmail %></strong></p>
    </div>
    <a href="logout" class="btn btn-logout" id="logoutBanner">Logout</a>
</div>

<%
    String successMsg = request.getParameter("success");
    if (successMsg != null && !successMsg.isEmpty()) {
%>
    <div class="alert alert-success" style="max-width:1100px; width:100%; margin-bottom:10px;">
        &#10003; <%= successMsg %>
    </div>
<% } %>
<%
    String errorMsg = request.getParameter("error");
    if (errorMsg != null && !errorMsg.isEmpty()) {
%>
    <div class="alert alert-error" style="max-width:1100px; width:100%; margin-bottom:10px;">
        &#9888; <%= errorMsg %>
    </div>
<% } %>

<div class="table-container">
    <div class="table-header">
        <h3>All Registered Users</h3>
        <form action="dashboard" method="get" class="search-form" id="searchForm">
            <input type="text" name="search" id="searchInput"
                   placeholder="Search by name or email..."
                   value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>" />
            <button type="submit" id="searchBtn">Search</button>
            <% if (request.getAttribute("searchQuery") != null) { %>
                <a href="dashboard" class="btn btn-outline" style="padding:8px 12px; font-size:0.82rem;">Clear</a>
            <% } %>
        </form>
    </div>

    <%
        List<User> users = (List<User>) request.getAttribute("users");
    %>

    <% if (users == null || users.isEmpty()) { %>
        <div class="empty-state">
            <p>No users found. <a href="register">Register a new user</a>.</p>
        </div>
    <% } else { %>

    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Email</th>
                <th>Password</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                int rowNum = 1;
                for (User u : users) {
            %>
            <tr>
                <td><%= rowNum++ %></td>
                <td><%= u.getName() %></td>
                <td><%= u.getEmail() %></td>
                <td>
                    <span class="pwd-mask" id="pwd-<%= u.getId() %>" data-real="<%= u.getPassword() %>">
                        &#9679;&#9679;&#9679;&#9679;&#9679;&#9679;
                    </span>
                    <button type="button" onclick="togglePwd(<%= u.getId() %>)"
                            style="background:none; border:none; cursor:pointer; font-size:0.85rem;">&#128065;</button>
                </td>
                <td>
                    <a href="update?id=<%= u.getId() %>" class="btn btn-warning" id="editBtn-<%= u.getId() %>">Edit</a>
                    <a href="delete?id=<%= u.getId() %>" class="btn btn-danger" id="deleteBtn-<%= u.getId() %>"
                       onclick="return confirm('Delete <%= u.getName() %>?');">Delete</a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <div style="padding:12px 20px; font-size:0.82rem; color:#718096; background:#f7fafc; border-top:1px solid #e2e8f0;">
        Showing <strong><%= users.size() %></strong> user(s)
    </div>

    <% } %>
</div>

<script>
    function togglePwd(id) {
        var span = document.getElementById('pwd-' + id);
        if (span.textContent.trim() === '\u25CF\u25CF\u25CF\u25CF\u25CF\u25CF') {
            span.textContent = span.dataset.real;
        } else {
            span.textContent = '\u25CF\u25CF\u25CF\u25CF\u25CF\u25CF';
        }
    }
</script>

</body>
</html>
