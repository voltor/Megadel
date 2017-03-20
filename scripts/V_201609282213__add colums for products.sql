ALTER TABLE products ADD COLUMN price1 double precision;
ALTER TABLE products ADD COLUMN price_opt1 double precision;
ALTER TABLE products ADD COLUMN shtrih_code text;
ALTER TABLE products
  ADD CONSTRAINT product_code_unicue_key UNIQUE(code);
ALTER TABLE products
  ADD CONSTRAINT product_shtrih_code_unicue_key UNIQUE(shtrih_code);
