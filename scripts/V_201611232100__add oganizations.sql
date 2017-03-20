CREATE TABLE organization
(
  id serial NOT NULL,
  tick_id bigint,
  CONSTRAINT organization_primary_key PRIMARY KEY (id),
  CONSTRAINT organization_tick_foreing_key FOREIGN KEY (tick_id)
      REFERENCES tick (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)