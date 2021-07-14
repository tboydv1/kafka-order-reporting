set FOREIGN_KEY_CHECKS = 0;

truncate table product;

insert into product(id, `name`, price, quantity_in_stock, description)
values
-- (21, 'Fire Extinguisher', 'FER', 500, 15,'this is a fire extinguisher'),
(22, 'Hammer',  1400, 24,'this is a Big Hammer'),
(23, 'Sledge hammer',  1500, 27,'this is a Sledge Hammer'),
(24, 'Blinds',  2400, 24,'this is a Window Blind'),
(25, 'Speaker',  1400, 24,'this is a Surround Speaker'),
(26, 'Roof Tops',  1400, 24,'this is a Roof Top');

set FOREIGN_KEY_CHECKS = 1;


