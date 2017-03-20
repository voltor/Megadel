CREATE TABLE coming
(
  id serial NOT NULL,
  date timestamp without time zone,
  sum double precision,
  provider_id bigint,
  user_id bigint,
  CONSTRAINT coming_primary_key PRIMARY KEY (id),
  CONSTRAINT coming_provider_foreing_key FOREIGN KEY (provider_id)
      REFERENCES provider (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT coming_user_foreing_key FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkdo4e6c4jfta3crs5mo3y1xkho FOREIGN KEY (provider_id)
      REFERENCES provider (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkgyvnyhixycijsry9rn3a0pveu FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)