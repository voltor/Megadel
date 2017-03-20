

DROP TABLE salling;

CREATE TABLE selling
(
  id serial NOT NULL,
  date timestamp without time zone,
  sum double precision,
  seller_id bigint,
  user_id bigint,
  CONSTRAINT selling_primary_key PRIMARY KEY (id),
  CONSTRAINT fkeb7d4w7tukpj3h49pal407v7e FOREIGN KEY (seller_id)
      REFERENCES seller (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkrhq2p4vnthm5scdpawa5f121j FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT selling_seller_foreing_key FOREIGN KEY (seller_id)
      REFERENCES seller (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT selling_user_foreing_key FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
