drop database if exists projecteam9;

CREATE DATABASE projecteam9;
use projecteam9;

CREATE TABLE abc
(	first_name varchar(30),
	last_name varchar(50),
	plate varchar(10),
	expiration varchar(20),
    afm int
);

LOAD DATA LOCAL infile "C:\\Users\\Apostolis\\Desktop\\db.csv" INTO TABLE abc columns terminated by ';';

update abc set expiration= str_to_date(expiration, '%d/%m/%Y');

CREATE TABLE driver
(
  afm int ,
  first_name varchar(50) NOT NULL,
  last_name varchar(30),
  primary key (afm)
 
);


CREATE TABLE vehicle
(
  
  id int auto_increment,
  owner_id int,
  plate varchar(10) unique,
  expiration date NOT NULL,
  primary key (id),
  foreign key (owner_id) references driver(afm)
);

insert into driver(afm,first_name,last_name) select distinct abc.afm,abc.first_name,abc.last_name from abc;

insert into vehicle(owner_id,plate,expiration) select distinct driver.afm,abc.plate,abc.expiration from abc,driver where driver.afm=abc.afm ;

drop table abc;