DROP TABLE IF EXISTS stats CASCADE;

CREATE TABLE IF NOT EXISTS stats
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app        VARCHAR(100),
    uri        VARCHAR(400),
    ip         VARCHAR(100),
    time_stamp TIMESTAMP
);