--liquibase formatted sql

--changeset chuckcha:1
CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL
);

--changeset chuckcha:2
CREATE TABLE IF NOT EXISTS locations
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(64) NOT NULL,
    user_id   INT REFERENCES users (id),
    latitude  DECIMAL     NOT NULL,
    longitude DECIMAL     NOT NULL
);

--changeset chuckcha:3
CREATE UNIQUE INDEX unique_user_location_index
    ON locations (user_id, latitude, longitude);

--changeset chuckcha:4
CREATE TABLE IF NOT EXISTS sessions
(
    id UUID PRIMARY KEY,
    user_id INT REFERENCES users (id),
    expires_at TIMESTAMP NOT NULL
);