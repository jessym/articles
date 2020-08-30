CREATE TABLE account
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR,
    email VARCHAR UNIQUE
);