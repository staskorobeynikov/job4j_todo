CREATE TABLE items(
	id serial primary key not null,
	description varchar(500) not null,
	created timestamp not null,
	done boolean
);