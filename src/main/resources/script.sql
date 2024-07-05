create database inventorymanagementsystem;
use inventorymanagementsystem;
create table products (
id int auto_increment primary key,
Name varchar(20),
Quantity int not null ,
Description varchar(40)
);


insert into products(Name,Quantity,Description) values("Jeans","40","verycomfortable");
