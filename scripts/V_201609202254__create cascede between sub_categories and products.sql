ALTER TABLE products DROP COLUMN sub_group;
ALTER TABLE products ADD COLUMN sub_category_id bigint;

ALTER TABLE ONLY products 
    ADD CONSTRAINT fk_sub_categories_products FOREIGN KEY (sub_category_id) REFERENCES sub_categories(id) ON DELETE CASCADE;
ALTER TABLE sub_categories
   ALTER COLUMN category_id TYPE bigint;