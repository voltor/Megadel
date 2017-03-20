CREATE TABLE selling_position
(
  id serial NOT NULL,
  price double precision,
  count integer,
  sum double precision,
  product_id bigint,
  selling_id bigint,
  CONSTRAINT selling_position_primary_key PRIMARY KEY (id),
  CONSTRAINT fkbepjs18t7udrksy0a5b64fro FOREIGN KEY (product_id)
      REFERENCES products (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkj5yv3ei7v0jhqr78dbttxunig FOREIGN KEY (selling_id)
      REFERENCES selling (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT selling_position_product_foreing_key FOREIGN KEY (product_id)
      REFERENCES products (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT selling_position_selling_foreing_key FOREIGN KEY (selling_id)
      REFERENCES selling (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)