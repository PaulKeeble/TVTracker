# Configuration, Users
 
# --- !Ups

CREATE TABLE Users (
    name VARCHAR(255) NOT NULL,
);

CREATE TABLE Directories (
    path VARCHAR(1024) NOT NULL,
);
 
# --- !Downs
 
DROP TABLE Users;

DROP TABLE Directories;