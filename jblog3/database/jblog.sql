-- jblog 스키마 생성
CREATE SCHEMA IF NOT EXISTS jblog;

-- jblog 스키마 사용
USE jblog;

-- user 테이블 생성
CREATE TABLE user (
    id VARCHAR(80) PRIMARY KEY NOT NULL ,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(64) NOT NULL,
    join_date DATE NOT NULL
);

-- blog 테이블 생성
CREATE TABLE blog (
    id VARCHAR(80) NOT NULL ,
    title VARCHAR(200) NOT NULL,
    logo VARCHAR(200) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES user(id)
);

-- category 테이블 생성
CREATE TABLE category (
    no INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    reg_date DATE NOT NULL,
    id VARCHAR(80) NOT NULL,
    FOREIGN KEY (id) REFERENCES user(id)
);

-- post 테이블 생성
CREATE TABLE post (
    no INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
    title VARCHAR(200) NOT NULL,
    contents TEXT NOT NULL,
    reg_date DATETIME NOT NULL,
    category_no INT NOT NULL,
    FOREIGN KEY (category_no) REFERENCES category(no)
);
