CREATE TABLE IF NOT EXISTS boxes(
  id serial PRIMARY KEY,
  name varchar(155) UNIQUE,
  width int,
  height int,
  space text
);