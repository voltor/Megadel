CREATE TABLE public.products
(
   id serial, 
   name text, 
   price double precision, 
   count integer, 
   sub_group text,
	CONSTRAINT id_product_key PRIMARY KEY (id)
)