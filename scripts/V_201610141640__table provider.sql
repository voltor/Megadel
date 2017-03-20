CREATE TABLE provider
(
  id serial NOT NULL,
  firm_name text,
  name text,
  email text,
  phone text,
  tick bigint,
  CONSTRAINT privader_primary_key PRIMARY KEY (id),
  CONSTRAINT provider_tick_fr_key FOREIGN KEY (tick)
      REFERENCES tick (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)