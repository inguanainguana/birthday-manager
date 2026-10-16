CREATE DATABASE IF NOT EXISTS manager_birthday
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE manager_birthday;

CREATE TABLE IF NOT EXISTS users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL,
    surname     VARCHAR(50)  NOT NULL,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    email       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        INT          NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS birthday (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(50)  NOT NULL,
    surname       VARCHAR(50)  NOT NULL,
    birth_date    DATE         NOT NULL,
    relationship  INT          NOT NULL DEFAULT 0,
    user_id       INT          NOT NULL,
    CONSTRAINT fk_birthday_user
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS gift (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(100)   NOT NULL,
    description  TEXT,
    price        DECIMAL(10, 2),
    birthday_id  INT            NOT NULL,
    user_id      INT            NOT NULL,
    CONSTRAINT fk_gift_birthday
        FOREIGN KEY (birthday_id) REFERENCES birthday (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_gift_user
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE
);
