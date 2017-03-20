CREATE TABLE coming_position
(
  id serial NOT NULL,
  price double precision,
  count integer,
  sum double precision,
  product_id bigint,
  coming_id bigint,
  CONSTRAINT coming_position_primary_key PRIMARY KEY (id),
  CONSTRAINT coming_position_coming_foreing_key FOREIGN KEY (coming_id)
      REFERENCES coming (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT coming_position_product_foreing_key FOREIGN KEY (product_id)
      REFERENCES products (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkh1j6cjj2bw64buc5ug57l2ch3 FOREIGN KEY (product_id)
      REFERENCES products (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fki8xijxdalc3tg90dey0wig3p7 FOREIGN KEY (coming_id)
      REFERENCES coming (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)