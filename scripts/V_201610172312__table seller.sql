CREATE TABLE seller
(
  id serial NOT NULL,
  can_give_a_loan boolean,
  name text,
  email text,
  phone text,
  tick bigint,
  CONSTRAINT seller_primary_key PRIMARY KEY (id),
  CONSTRAINT fkc0bpv773o4a61fdn26aud7miy FOREIGN KEY (tick)
      REFERENCES tick (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkfexul55aegweisg81jkqsns8k FOREIGN KEY (tick)
      REFERENCES tick (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT seller_tick_fr_key FOREIGN KEY (tick)
      REFERENCES tick (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)