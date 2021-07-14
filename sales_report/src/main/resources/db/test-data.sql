set foreign_key_checks = 0;

truncate table report_data;

insert into report_data(id, date_created, order_id, total_price, total_products)
values('212', '2021-06-14', '90', '2800', '12'),
('213', '2021-06-14', '90', '2800', '12'),
('214', '2021-07-14', '90', '800', '12'),
('215', '2021-07-14', '90', '800', '12'),
('216', '2021-08-14', '90', '200', '12'),
('217', '2021-08-14', '90', '200', '12'),
('218', '2021-09-14', '90', '500', '12'),
('219', '2021-09-14', '90', '500', '12'),
('220', '2021-10-14', '90', '2800', '12'),
('221', '2021-10-14', '90', '2800', '12'),
('222', '2021-11-14', '90', '2800', '12'),
('223', '2021-11-14', '90', '2800', '12'),
('224', '2021-12-14', '90', '2800', '12'),
('225', '2021-12-14', '90', '2800', '12'),
('226', '2021-01-14', '90', '2800', '12');

set foreign_key_checks = 1;
