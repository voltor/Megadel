CREATE TABLE tick_history
(
  id serial NOT NULL,
  tick_id bigint,
  user_id bigint,
  date timestamp without time zone,
  type text,
  value double precision,
  CONSTRAINT tick_history_primary_key PRIMARY KEY (id),
  CONSTRAINT fkkfaj295xq86a51ywhk41c187f FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkm1dihancxpk3q2g16446d00js FOREIGN KEY (tick_id)
      REFERENCES tick (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tick_history_tick_foreing_key FOREIGN KEY (tick_id)
      REFERENCES tick (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tick_history_user_foreing_key FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)