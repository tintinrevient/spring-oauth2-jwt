DROP TABLE IF EXISTS user;
create table user (
  id INTEGER PRIMARY KEY,
  version INTEGER,
  username VARCHAR(128),
  fullname VARCHAR(128),
  password VARCHAR(128),
  roles VARCHAR(64),
  app VARCHAR(64)
);