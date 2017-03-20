CREATE TABLE tick
(
  id serial NOT NULL,
  value_ua double precision,
  value_usa double precision,
  CONSTRAINT tick_id_key PRIMARY KEY (id)
)