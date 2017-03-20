CREATE TABLE categories
(
  id serial NOT NULL,
  name text,
  code text,
  CONSTRAINT categories_primary_key PRIMARY KEY (id)
)

CREATE TABLE sub_categories
(
  id serial NOT NULL,
  name text,
  code text,
  category_id integer,
  CONSTRAINT sub_categories_primary_key PRIMARY KEY (id),
  CONSTRAINT fk_categories_sub_categories FOREIGN KEY (category_id)
      REFERENCES categories (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
