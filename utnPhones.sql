create database utn_phones;
use utn_phones;
#drop database utn_phones;
SET GLOBAL time_zone = '-3:00';

create table provinces(
	id_province int auto_increment,
    province_name varchar(50),
    constraint pk_id_province primary key (id_province)
);
create table cities(
	id_city int auto_increment,
    id_province int,
    city_name varchar(100),
    area_code varchar(10), 
    constraint pk_id_city primary key (id_city),
    constraint fk_cities_province foreign key (id_province) references provinces(id_province)
);
create table users(
	id_user int auto_increment,
    id_city int,
    name varchar(50),
    last_name varchar(50),
    dni varchar(10),
    user_name varchar(40) unique,
    pwd varchar(40),
    user_type enum('employee', 'client'),
	removed_user boolean, -- baja logica
    constraint pk_id_user primary key (id_user),
    constraint fk_users_city foreign key (id_city) references cities (id_city)
);
create table phone_lines(
	id_phone_line int auto_increment,
    id_user int,
    phone_number varchar(32) unique, #siempre se guardan con el area code
    line_type enum('mobile', 'home'), 
	state enum('register', 'suspended', 'removed'),
    constraint pk_id_phone_line primary key (id_phone_line),
    constraint fk_phone_lines_user foreign key (id_user) references Users (id_user)
);
create table tariffs(
	id_tariff int auto_increment,
    id_origin_city int,
    id_destination_city int,
    price_per_minute float,
    cost_per_minute float,
    constraint pk_id_tariff primary key (id_tariff),
    constraint fk_tariffs_origin_city foreign key (id_origin_city) references cities (id_city),
	constraint fk_tariffs_destination_city foreign key (id_destination_city) references cities (id_city)
);
create table bills(
	id_bill int auto_increment,
    id_phone_line int,
    calls_amount int,
    total_cost decimal,
    total_price decimal,
    bill_date date,
    bill_expiration date,
    state enum('payed', 'expired'), #paga o vencida, puse state porque status es reservada
	constraint pk_id_bill primary key (id_bill),
    constraint fk_bills_phone_line foreign key (id_phone_line) references phone_lines (id_phone_line)
);	
 create table phone_calls(
	id_phone_call int auto_increment,
	id_tariff int,
    id_bill int,
    id_origin_phone_line int,
	id_destination_phone_line int,
	origin_phone_number varchar(32),
	destination_phone_number varchar(32),
	total_cost decimal,
    total_price decimal, 
    duration int,
	date_call datetime,
    constraint pk_id_phone_call primary key (id_phone_call),
    constraint fk_phone_calls_tariff foreign key (id_tariff) references tariffs (id_tariff),
    constraint fk_phone_calls_bills foreign key (id_bill) references bills (id_bill),
    constraint fk_phone_calls_origin_phone_line foreign key (id_origin_phone_line) references phone_lines(id_phone_line),
	constraint fk_phone_calls_destination_phone_line foreign key (id_destination_phone_line ) references phone_lines(id_phone_line)
);

select u.user_name userName, b.calls_amount callsAmount, b.total_price total_price, b.bill_date date, b.bill_expiration dateExpiration
from bills as b
join phone_lines as pl
on b.id_phone_line=pl.id_phone_line
join users as u
on pl.id_user = u.id_user
where u.id_user = 1 AND b.bill_date BETWEEN  "2020-04-02" AND "2020-05-03";

select u.user_name userName, pc.total_price totalPrice, pc.duration duration, pc.date_call date
from phone_calls as pc
join phone_lines as pl
on pc.id_origin_phone_line=pl.id_phone_line
join users as u
on pl.id_user = u.id_user
where u.id_user = 1 AND pc.date_call BETWEEN "2020-04-02" AND "2020-05-03";

select u.user_name userName, pc.total_cost totalCost, pc.total_price totalPrice, pc.duration duration, pc.date_call date
from phone_calls as pc
join phone_lines as pl
on pc.id_origin_phone_line=pl.id_phone_line
join users as u
on pl.id_user = u.id_user
where u.id_user = 1;

Delimiter //
create trigger tbi_phone_calls before insert on phone_calls for each row
begin
	set new.date_call = now();
end //
// drop trigger tbi_phone_calls;

select u.user_name userName, b.calls_amount callsAmount, b.total_price total_price, b.bill_date date, b.bill_expiration dateExpiration
from bills as b
join phone_lines as pl
on b.id_phone_line=pl.id_phone_line
join users as u
on pl.id_user = u.id_user
where u.id_user = 1 AND b.bill_date BETWEEN  "2020-04-02" AND "2020-05-03";

select u.user_name userName, pc.total_price totalPrice, pc.duration duration, pc.date_call date
from phone_calls as pc
join phone_lines as pl
on pc.id_origin_phone_line=pl.id_phone_line
join users as u
on pl.id_user = u.id_user
where u.id_user = 1 AND pc.date_call BETWEEN "2020-04-02" AND "2020-05-03";

select u.user_name userName, pc.total_cost totalCost, pc.total_price totalPrice, pc.duration duration, pc.date_call date
from phone_calls as pc
join phone_lines as pl
on pc.id_origin_phone_line=pl.id_phone_line
join users as u
on pl.id_user = u.id_user
where u.id_user = 1;

select u.user_name userName, b.calls_amount callsAmount, b.total_price total_price, b.bill_date date, b.bill_expiration dateExpiration
            from bills as b
            join phone_lines as pl
            on b.id_phone_line=pl.id_phone_line
            join users as u
            on pl.id_user = u.id_user
			where u.id_user = 1 ;
            
select * from bills;

select * from phone_lines;
