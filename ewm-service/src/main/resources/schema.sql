DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS compilations_events CASCADE;
DROP TABLE IF EXISTS requests CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(100),
    name  VARCHAR(100),
    CONSTRAINT uq_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100),
    CONSTRAINT uq_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title  VARCHAR(120),
    pinned BOOLEAN,
    CONSTRAINT uq_title UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS location
(
    lat FLOAT,
    lon FLOAT,
    PRIMARY KEY (lat, lon)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_user            BIGINT,
    id_category        BIGINT,
    lat                FLOAT,
    lon                FLOAT,
    annotation         VARCHAR(2000),
    title              VARCHAR(120),
    description        VARCHAR(7000),
    created_on         TIMESTAMP,
    event_date         TIMESTAMP,
    participant_limit  INT,
    paid               BOOLEAN,
    request_moderation BOOLEAN,
    state              VARCHAR(20),
    published_on       TIMESTAMP,
    CONSTRAINT fk_events_to_users FOREIGN KEY (id_user) REFERENCES users (id),
    CONSTRAINT fk_events_to_category FOREIGN KEY (id_category) REFERENCES categories (id),
    CONSTRAINT fk_events_to_location FOREIGN KEY (lat, lon) REFERENCES location (lat, lon)
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_event       BIGINT,
    id_compilation BIGINT,
    CONSTRAINT fk_to_compilation FOREIGN KEY (id_compilation) REFERENCES compilations (id),
    CONSTRAINT fk_to_events FOREIGN KEY (id_event) REFERENCES events (id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_user  BIGINT,
    id_event BIGINT,
    status   VARCHAR(20),
    created  TIMESTAMP,
    CONSTRAINT fk_requests_to_users FOREIGN KEY (id_user) REFERENCES users (id),
    CONSTRAINT fk_requests_to_events FOREIGN KEY (id_event) REFERENCES events (id)
);

CREATE TABLE if NOT EXISTS comments
(
    id bigint generated always AS IDENTITY PRIMARY KEY,
    id_user bigint,
    id_event bigint,
    description VARCHAR(7000),
    created TIMESTAMP,
    CONSTRAINT fk_comments_to_users FOREIGN KEY (id_user) REFERENCES users (id),
    CONSTRAINT fk_comments_to_events FOREIGN KEY (id_event) REFERENCES events (id)
);