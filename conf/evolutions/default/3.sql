# User Watched
 
# --- !Ups

CREATE TABLE Watched(
   user VARCHAR(255) NOT NULL,
   episode LONG NOT NULL
);

# --- !Downs

DELETE TABLE Watched;