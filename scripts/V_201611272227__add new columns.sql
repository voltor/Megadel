ALTER TABLE selling ADD COLUMN type integer;
ALTER TABLE tick_history ADD COLUMN transfer_cash_id bigint;
ALTER TABLE tick_history
  ADD CONSTRAINT tick_history_transfer_cash_foreing_key FOREIGN KEY (transfer_cash_id)
      REFERENCES transfer_cash (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tick_history ALTER COLUMN type TYPE integer USING (trim(type)::integer);

DELETE FROM products
WHERE products.id in (

select dups.id from (
  SELECT id,
  ROW_NUMBER() OVER(PARTITION BY name ORDER BY id asc) AS Row
  FROM products
) dups
where 
dups.Row > 1
);
ALTER TABLE products
  ADD CONSTRAINT product_name_unique_key UNIQUE(name);
