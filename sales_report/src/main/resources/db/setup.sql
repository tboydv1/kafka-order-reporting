create database if not exists reportdb;

create user if not exists 'DBUser'@'localhost' identified by 'DBPassword';
grant all privileges on reportdb.* to 'DBUser'@'localhost';
flush privileges ;