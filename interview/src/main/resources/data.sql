
DROP TABLE IF EXISTS users ;
DROP sequence IF EXISTS user_id_seq ;
create sequence user_id_seq start 1;

create table users (
	id bigint auto_increment  not null,
	username varchar(40) not null,
	email varchar(80) not null,
	password varchar(255) not null
);
alter table users add constraint user_id_unique unique(id);

insert into users (username, email, password) values ('superuser', 'superuser@email.com','super2020');

DROP TABLE IF EXISTS subscription;
DROP sequence IF EXISTS subscription_id_seq ;
create sequence subscription_id_seq start 1;
create table subscription (
	id bigint auto_increment not null,
	amount DECIMAL(20, 2),
	subscription_type varchar(40) not null,
	subscription_start_date date not null,
	subscription_end_date date not null,
	invoice_date_record varchar(200)
);
alter table users add primary key (id);