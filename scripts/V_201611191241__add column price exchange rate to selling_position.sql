ALTER TABLE selling_position
   ADD COLUMN exchange_rate_id bigint;
ALTER TABLE selling_position
  ADD CONSTRAINT selling_position_exchange_rate_foreing_key FOREIGN KEY (exchange_rate_id)
      REFERENCES exchange_rate (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;