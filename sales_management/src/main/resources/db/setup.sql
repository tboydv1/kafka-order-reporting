create database if not exists inventorydb;

create user if not exists 'DBUser'@'localhost' identified by 'DBPassword';
grant all privileges on inventorydb.* to 'DBUser'@'localhost';
flush privileges ;