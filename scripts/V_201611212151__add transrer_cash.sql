CREATE TABLE transfer_cash
(
  id serial NOT NULL,
  autor bigint,
  date timestamp without time zone,
  receiver bigint,
  sum double precision,
  status integer,
  CONSTRAINT transfer_cash_primary_key PRIMARY KEY (id),
  CONSTRAINT fkapll4i7ukpd2bsk48g8ks2uo0 FOREIGN KEY (autor)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkonn6m5826wc7yn27awerfr8hx FOREIGN KEY (receiver)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT transfer_cash_autor_foreing_key FOREIGN KEY (autor)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT transfer_cash_receiver_foreing_key FOREIGN KEY (receiver)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

ALTER TABLE selling ADD COLUMN transfer_cash_id bigint;

ALTER TABLE selling
  ADD CONSTRAINT selling_transfer_cash_foreing_key FOREIGN KEY (transfer_cash_id)
      REFERENCES transfer_cash (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

