CREATE TABLE logs
(
  id serial NOT NULL,
  date timestamp without time zone,
  instance text,
  user_id bigint,
  description text,
  CONSTRAINT logs_primery_key PRIMARY KEY (id)
)