DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id serial NOT NULL,
    name varchar(30) NOT NULL UNIQUE,
    password varchar(255) NOT NULL
);