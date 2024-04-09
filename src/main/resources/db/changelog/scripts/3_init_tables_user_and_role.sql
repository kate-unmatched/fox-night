CREATE SEQUENCE IF NOT EXISTS global_sequence;

CREATE TABLE IF NOT EXISTS users_
(
    id BIGSERIAL PRIMARY KEY,
    creation_date timestamp,
    modification_date timestamp,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone_number VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    is_active BOOLEAN
    );

CREATE TABLE IF NOT EXISTS role
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);