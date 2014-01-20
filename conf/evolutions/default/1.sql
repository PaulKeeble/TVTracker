# Configuration, Users
 
# --- !Ups

CREATE TABLE Users (
    name varchar(255) NOT NULL,
);

CREATE TABLE Directories (
    path varchar(512) NOT NULL,
);
 
# --- !Downs
 
DROP TABLE Users;

DROP TABLE Directories;