CREATE TABLE exchange_rate
(
  id serial NOT NULL,
  date_time timestamp without time zone,
  currency double precision,
  CONSTRAINT exchange_rate_key PRIMARY KEY (id)
)