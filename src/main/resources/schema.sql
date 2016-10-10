create table T_PRODUCTS_EVENTS (
	id character varying(40) PRIMARY KEY NOT NULL, 
	aggregate character varying(40) NOT NULL, 
	created timestamp NOT NULL, 
	type character varying not null,
	payload character varying NOT NULL
);
