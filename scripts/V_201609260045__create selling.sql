CREATE TABLE salling
(
  id serial NOT NULL,
  product_id bigint,
  user_id bigint,
  price double precision,
  selling_price double precision,
  currency_id bigint,
  client text,
  discount double precision,
  currency double precision,
  count integer,
  date_time timestamp without time zone,
  CONSTRAINT selling_primary_key PRIMARY KEY (id),
  CONSTRAINT currency_selling_foreign_key FOREIGN KEY (currency_id)
      REFERENCES exchange_rate (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkc3qnw07pngraoewn4fea0ln4x FOREIGN KEY (product_id)
      REFERENCES products (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkn5sfx4rcj2sq047jh04vh1mfb FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkrjgu63stljer3uehlcohievnu FOREIGN KEY (currency_id)
      REFERENCES exchange_rate (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT product_selling_foreign_key FOREIGN KEY (product_id)
      REFERENCES products (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_selling_foreign_key FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)