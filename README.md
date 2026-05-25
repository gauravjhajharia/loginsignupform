# Login and Sign Up Form

A beginner-friendly Java Servlet CRUD project with authentication.

## 🌐 Live Demo

👉 **[https://loginsignupform-5yh0.onrender.com](https://loginsignupform-5yh0.onrender.com)**

## 🔑 Test Credentials

| Email | Password |
|-------|----------|
| alice@example.com | alice123 |
| bob@example.com | bob123 |
| charlie@example.com | charlie123 |

Or register your own account!

## 🛠️ Tech Stack

- **Backend** — Java Servlet API + JDBC
- **Frontend** — JSP + HTML + CSS
- **Database** — MySQL (Aiven Cloud)
- **Server** — Apache Tomcat 10
- **Build** — Maven
- **Deploy** — Render (Docker)

## 📁 Project Structure

```
src/main/java/
  controller/   ← Servlets (Login, Register, Dashboard, Update, Delete)
  dao/          ← UserDAO (database operations)
  db/           ← DBConnection (MySQL connection)
  model/        ← User POJO

src/main/webapp/
  *.jsp         ← JSP pages
  style.css     ← Styling
  WEB-INF/      ← web.xml
```

## ⚙️ Features

- ✅ User Registration
- ✅ User Login / Logout
- ✅ Session Management
- ✅ View All Users (Dashboard)
- ✅ Edit User
- ✅ Delete User
- ✅ Input Validation
