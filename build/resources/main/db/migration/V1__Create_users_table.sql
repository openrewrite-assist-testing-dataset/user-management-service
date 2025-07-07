CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);

-- Insert a default admin user (password: admin123)
INSERT INTO users (username, email, password_hash, created_at, updated_at) 
VALUES ('admin', 'admin@example.com', '$2a$10$7xJhYj5FUHsaAm0/tGxT8uDYyVPzJJXmJzlQOZWlcULzTfJ6.H6X2', NOW(), NOW());