CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE user_role as ENUM ('ADMIN', 'VETERINARIAN', 'USER', 'TAXI_DRIVER');

CREATE TABLE users (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50),
    CRV VARCHAR(50),
    drivers_license VARCHAR(50),
    roles user_role[] NOT NULL DEFAULT '{USER}',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE address (
     id UUID NOT NULL PRIMARY KEY,
     street VARCHAR(255) NOT NULL,
     number VARCHAR(255) NOT NULL,
     zip_code VARCHAR(255) NOT NULL,
     state VARCHAR(255) NOT NULL,
     city VARCHAR(255) NOT NULL,
     complement TEXT NOT NULL,
     address_name VARCHAR(50),
     user_id TEXT NOT NULL,
     main_address_at TIMESTAMP,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);