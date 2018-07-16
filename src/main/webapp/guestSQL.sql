create table guestbook(
	id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	name varchar(50) NOT NULL,
	content text,
	regdate datetime default now(),
	PRIMARY KEY(id));

	
CREATE SEQUENCE seq_id START WITH 1 INCREMENT BY 1;

insert into guestbook values(seq_id.nextval,'');
