# Library (Show, Season, Episode)
 
# --- !Ups

CREATE TABLE Shows(
  id IDENTITY PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  location VARCHAR(1024) NOT NULL,
);

CREATE TABLE Episodes(
  id IDENTITY PRIMARY KEY,
  show BIGINT NOT NULL,
  season INT NOT NULL,
  number INT NOT NULL,
  title VARCHAR(1024),
  filename VARCHAR(1024) NOT NULL,
  created DATE NOT NULL
);

# --- !Downs

DROP TABLE Episodes;

DROP TABLE Shows;