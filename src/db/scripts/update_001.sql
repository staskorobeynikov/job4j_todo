CREATE TABLE items(
	id serial primary key not null,
	description text not null,
	created timestamp not null,
	done boolean
);

CREATE TABLE users(
    id serial primary key not null,
    email text not null unique,
    password text not null,
    username text not null
);