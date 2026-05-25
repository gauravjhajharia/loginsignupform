package model;

/**
 * User.java
 * ----------
 * This is the Model class (the "M" in MVC).
 * It represents a single user record from the database.
 *
 * A POJO (Plain Old Java Object) — just fields + getters + setters + constructors.
 *
 * Beginner Tip:
 *   - Getter = method to READ a field value  (e.g., getName())
 *   - Setter = method to WRITE a field value (e.g., setName("Alice"))
 *   - Constructor = special method called when you create an object with "new"
 */
public class User {

    // Fields matching the 'users' table columns
    private int    id;
    private String name;
    private String email;
    private String password;

    // ─────────────────────────────────────────────────
    // Constructors
    // ─────────────────────────────────────────────────

    /** No-argument constructor — required by many frameworks and JSP tags */
    public User() {}

    /**
     * Full constructor — used when creating a brand-new user before saving to DB.
     * id is omitted because AUTO_INCREMENT handles it.
     */
    public User(String name, String email, String password) {
        this.name     = name;
        this.email    = email;
        this.password = password;
    }

    /**
     * Full constructor including id — used when reading data FROM the database.
     */
    public User(int id, String name, String email, String password) {
        this.id       = id;
        this.name     = name;
        this.email    = email;
        this.password = password;
    }

    // ─────────────────────────────────────────────────
    // Getters
    // ─────────────────────────────────────────────────

    public int    getId()       { return id;       }
    public String getName()     { return name;     }
    public String getEmail()    { return email;    }
    public String getPassword() { return password; }

    // ─────────────────────────────────────────────────
    // Setters
    // ─────────────────────────────────────────────────

    public void setId(int id)             { this.id       = id;       }
    public void setName(String name)      { this.name     = name;     }
    public void setEmail(String email)    { this.email    = email;    }
    public void setPassword(String pass)  { this.password = pass;     }

    /** Helper — useful for debugging: prints all field values */
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
