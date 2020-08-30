CREATE TABLE account
(
    id    SERIAL PRIMARY KEY,
    email VARCHAR UNIQUE,
    name  VARCHAR
);