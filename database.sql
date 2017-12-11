DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id serial NOT NULL,
    name varchar(30) NOT NULL UNIQUE,
    password CHAR(60) NOT NULL
);