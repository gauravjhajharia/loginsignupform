-- ============================================================
-- database_setup.sql — LoginSignupCRUD
-- ============================================================
-- Run this script in MySQL Workbench, MySQL CLI, or any MySQL
-- client to set up the database for this project.
--
-- Steps to run:
--   Option A (MySQL CLI):
--     mysql -u root -p  < database_setup.sql
--
--   Option B (MySQL Workbench):
--     Open Workbench → File → Open SQL Script → Run
-- ============================================================

-- Step 1: Create the database if it doesn't already exist
CREATE DATABASE IF NOT EXISTS testdb
    CHARACTER SET utf8mb4       -- supports all Unicode characters including emojis
    COLLATE utf8mb4_unicode_ci; -- case-insensitive collation

-- Step 2: Switch to the testdb database
USE testdb;

-- Step 3: Drop the table if it exists (useful for fresh setup)
-- WARNING: This will delete all existing user data!
DROP TABLE IF EXISTS users;

-- Step 4: Create the users table
CREATE TABLE users (
    id       INT          PRIMARY KEY AUTO_INCREMENT,  -- unique row id, auto-incremented
    name     VARCHAR(100) NOT NULL,                    -- user's full name
    email    VARCHAR(100) NOT NULL UNIQUE,             -- email must be unique (no duplicates)
    password VARCHAR(100) NOT NULL                     -- plain text for demo; use BCrypt in production!
);

-- Step 5: (Optional) Insert sample data for testing
-- These rows let you log in immediately without registering first.
INSERT INTO users (name, email, password) VALUES
    ('Alice Johnson', 'alice@example.com', 'alice123'),
    ('Bob Smith',     'bob@example.com',   'bob123'),
    ('Charlie Brown', 'charlie@example.com','charlie123');

-- Step 6: Verify the setup
SELECT 'Database setup complete! Users table created.' AS Status;
SELECT * FROM users;
