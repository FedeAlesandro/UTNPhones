create database utn_phones;
use utn_phones; 
SET GLOBAL time_zone = '-03:00';
SET GLOBAL event_scheduler = ON;
SET AUTOCOMMIT=0;
#drop database utn_phones;

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
    user_type enum('employee', 'client', 'infrastructure'),
	removed_user boolean default false, -- baja logica
    constraint pk_id_user primary key (id_user),
    constraint fk_users_city foreign key (id_city) references cities (id_city)
);
create table phone_lines(
	id_phone_line int auto_increment,
    id_user int,
    phone_number varchar(32) unique, #siempre se guardan con el area code
    line_type enum('mobile', 'home'), 
	state enum('register', 'suspended', 'removed') default 'register',
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
    state enum('sent', 'payed', 'expired'), #paga o vencida, puse state porque status es reservada
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

delimiter //
create procedure sp_find_city_by_phone_number (origin_phone_number varchar(32), destination_phone_number varchar(32), out origin_id_city int, out destination_id_city int)
begin

	set origin_id_city = (select distinct c.id_city
		from cities as c
        join users as u
        on u.id_city = c.id_city
        join phone_lines as pl
        on pl.id_user = u.id_user
        join phone_calls as pc
        on pc.id_origin_phone_line = pl.id_phone_line
        where pc.origin_phone_number like CONCAT(c.area_code,'%') and pc.origin_phone_number = origin_phone_number);
    
    set destination_id_city = (select distinct c.id_city
		from cities as c
        join users as u
        on u.id_city = c.id_city
        join phone_lines as pl
        on pl.id_user = u.id_user
        join phone_calls as pc
        on pc.id_destination_phone_line = pl.id_phone_line
        where pc.destination_phone_number like CONCAT(c.area_code,'%') and pc.destination_phone_number = destination_phone_number);
end //

delimiter //
create procedure sp_generate_values_calls (origin_phone_number varchar(32), destination_phone_number varchar(32), duration int, out id_origin_phone_line int, 
											out id_destination_phone_line int, out total_cost decimal, out total_price decimal, out id_tariff int)
begin

    call sp_find_city_by_phone_number (origin_phone_number, destination_phone_number, @origin_id_city, @destination_id_city);

	if exists(select pl.id_phone_line from phone_lines as pl where pl.phone_number = origin_phone_number and state = 'register') then
        set id_origin_phone_line = (select pl.id_phone_line from phone_lines as pl where pl.phone_number = origin_phone_number);
	else
		signal sqlstate "45000" set message_text = "The number entered doesn't exist", mysql_errno = 1001;
	end if;

    if exists(select pl.id_phone_line from phone_lines as pl where pl.phone_number = destination_phone_number and state = 'register') then
		set id_destination_phone_line = (select pl.id_phone_line from phone_lines as pl where pl.phone_number = destination_phone_number);
	else
		signal sqlstate "45000" set message_text = "The number entered doesn't exist", mysql_errno = 1001;
	end if;
    
    select t.cost_per_minute * duration, t.price_per_minute * duration, t.id_tariff
    into total_cost, total_price, id_tariff
    from tariffs as t
    where t.id_origin_city = @origin_id_city and t.id_destination_city = @destination_id_city;
    
end //

delimiter //
create trigger tbi_phone_calls before insert on phone_calls for each row
begin
	    
	call sp_generate_values_calls(new.origin_phone_number, new.destination_phone_number, new.duration,
				@id_origin_phone_line, @id_destination_phone_line, @total_cost, @total_price, @id_tariff);
                
	set new.date_call = now();
    set new.id_origin_phone_line = @id_origin_phone_line;
	set new.id_destination_phone_line = @id_destination_phone_line;
	set new.total_cost = @total_cost;
    set new.total_price = @total_price;
    set new.id_tariff = @id_tariff;
    
end //

delimiter //
create procedure sp_generate_bills ()
begin
	declare done int default 0;
    declare v_id_phone_line int;
	declare cur_phone_lines cursor for select id_phone_line from phone_lines;
    declare continue handler for not found set done = 1;
    
	start transaction;
    
    open cur_phone_lines;
    get_id : loop
		fetch cur_phone_lines into v_id_phone_line;
        if done = 1 then
			leave get_id;
		end if;
	
		insert into bills(id_phone_line, calls_amount, total_cost, total_price, bill_date, bill_expiration, state)
		select v_id_phone_line, COUNT(pc.id_phone_call) as calls_amount, ifnull(SUM(pc.total_cost), 0) as total_cost, ifnull(SUM(pc.total_price),0)  as total_price,
			CURDATE() as bill_date, DATE_ADD(CURDATE(), INTERVAL 15 DAY) as bill_expiration, 'sent' as state
		from phone_lines as pl
		join phone_calls as pc
		on pl.id_phone_line = pc.id_origin_phone_line
		where pl.id_phone_line = v_id_phone_line and pc.id_bill is null;
		
        update phone_calls set id_bill = last_insert_id() where id_origin_phone_line = v_id_phone_line and id_bill is null;
	end loop;
    
    close cur_phone_lines;
    
    commit;
    
end //

delimiter //
create procedure sp_generate_expired_bills ()
begin
	declare done int default 0;
    declare v_id_phone_line int;
	declare cur_phone_lines cursor for select id_phone_line from phone_lines;
    declare continue handler for not found set done = 1;
    
	start transaction;
    
    open cur_phone_lines;
    get_id : loop
		fetch cur_phone_lines into v_id_phone_line;
        if done = 1 then
			leave get_id;
		end if;
	
        update bills set state = 'expired' where id_phone_line = v_id_phone_line and state = 'sent';
	
    end loop;
    
    close cur_phone_lines;
    
    commit;
    
end //

delimiter //
create event if not exists event_bills
on schedule every 1 month 
starts '2020-07-01 00:00:00'
do
begin
	call sp_generate_bills();
end//

delimiter //
create event if not exists event_expired_bills
on schedule every 1 month 
starts '2020-07-15 00:00:00'
do
begin
	call sp_generate_expired_bills();
end//

create user 'infrastructure'@'localhost';
set password for 'infrastructure'@'localhost' = password('infrastructure');

create user 'employee'@'localhost';
set password for 'employee'@'localhost' = password('employee');

create user 'client'@'localhost';
set password for 'client'@'localhost' = password('client');

# INFRASTRUCTURE GRANTS
grant select, insert on utn_phones.phone_calls to 'infrastructure'@'localhost';
grant select on utn_phones.users to 'infrastructure'@'localhost';

# EMPLOYEE GRANTS
grant select, insert, update on utn_phones.users to 'employee'@'localhost';
grant select on utn_phones.phone_calls to 'employee'@'localhost';
grant select, insert, update on utn_phones.phone_lines to 'employee'@'localhost';
grant select, update on utn_phones.bills to 'employee'@'localhost';
grant select on utn_phones.tariffs to 'employee'@'localhost';
grant select on utn_phones.cities to 'employee'@'localhost';
grant select on utn_phones.provinces to 'employee'@'localhost';

# CLIENTS GRANTS
grant select on utn_phones.phone_calls to 'client'@'localhost';
grant select on utn_phones.bills to 'client'@'localhost';
grant select on utn_phones.users to 'client'@'localhost';
grant select on utn_phones.cities to 'client'@'localhost';
grant select on utn_phones.provinces to 'client'@'localhost';
grant select on utn_phones.phone_lines to 'client'@'localhost';
