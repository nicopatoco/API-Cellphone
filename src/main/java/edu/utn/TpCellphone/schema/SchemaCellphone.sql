CREATE DATABASE IF NOT EXISTS CellphoneCompany;

USE CellphoneCompany;

CREATE TABLE IF NOT EXISTS Provinces (
	  id_province INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
	  PRIMARY KEY (id_province)
);

CREATE TABLE IF NOT EXISTS Cities (
	  id_city INT AUTO_INCREMENT,
    id_province INT NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE,
    prefix INT NOT NULL UNIQUE,
    PRIMARY KEY (id_city),
    CONSTRAINT id_province_cities FOREIGN KEY(id_province) references Provinces(id_province)
);

CREATE TABLE IF NOT EXISTS Users (
	  id_user INT AUTO_INCREMENT,
    id VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    id_city INT NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    user_type ENUM("admin", "client") NOT NULL,
    PRIMARY KEY (id_user),
    CONSTRAINT id_city_user FOREIGN KEY(id_city) references Cities(id_city)
);

CREATE TABLE IF NOT EXISTS Prices (
	  id_price INT AUTO_INCREMENT,
    id_origin_city INT NOT NULL,
    id_destination_city INT NOT NULL,
	  price_per_minute FLOAT NOT NULL,
    PRIMARY KEY (id_price),
    CONSTRAINT id_origin_city_prices FOREIGN KEY(id_origin_city) references Cities(id_city),
    CONSTRAINT id_destination_city_prices FOREIGN KEY(id_destination_city) references Cities(id_city)
);


CREATE TABLE IF NOT EXISTS Cellphones (
	  id_cellphone INT AUTO_INCREMENT,
    cellphone_number VARCHAR(50) NOT NULL UNIQUE,
    line_type ENUM("mobile", "home") NOT NULL,
	  id_user INT NOT NULL,
    PRIMARY KEY (id_cellphone),
    CONSTRAINT id_user_cellphones FOREIGN KEY(id_user) references Users(id_user)
);

CREATE TABLE IF NOT EXISTS Bills (
	  id_bill INT AUTO_INCREMENT,
    id_cellphone INT NOT NULL,
    id_user INT NOT NULL,
    amount_of_calls INT,
    final_price FLOAT,
    bill_date DATE NOT NULL,
    due_date DATE NOT NULL,
    PRIMARY KEY (id_bill),
    CONSTRAINT id_cellphone_bills FOREIGN KEY(id_cellphone) references Cellphones(id_cellphone),
    CONSTRAINT id_user_bills FOREIGN KEY(id_user) references Users(id_user)
);

CREATE TABLE IF NOT EXISTS Calls (
	  id_call INT AUTO_INCREMENT,
    id_cellphone_origin INT,
    id_cellphone_destination INT,
    id_price INT,
    id_bill INT,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    final_value FLOAT,
    number_origin varchar(50) NOT NULL,
    number_destination varchar(50) NOT NULL,
    PRIMARY KEY (id_call),
    CONSTRAINT id_cellphone_origin_calls FOREIGN KEY(id_cellphone_origin) references Cellphones(id_cellphone),
    CONSTRAINT id_cellphone_destination_calls FOREIGN KEY(id_cellphone_destination) references Cellphones(id_cellphone),
    CONSTRAINT id_price_calls FOREIGN KEY(id_price) references Prices(id_price),
    CONSTRAINT id_bill_calls FOREIGN KEY(id_bill) references Bills(id_bill)
);

//DELITIMER
CREATE TRIGGER tbi_cellphones before insert on cellphones FOR EACH ROW
BEGIN
	declare setPrefix int;
	set setPrefix = 0;
	select prefix into setPrefix from users u join cities c on c.id_city = u.id_city where id_user = new.id_user;

	set new.cellphone_number = concat(setPrefix, new.cellphone_number);
END//
DELIMITER

CREATE TRIGGER tbi_cellphones before insert on cellphones FOR EACH ROW
BEGIN
    declare setPrefix int;
    set setPrefix = 0;
    select prefix into setPrefix from users u join cities c on c.id_city = u.id_city where id_user = new.id_user;

    set new.cellphone_number = concat(setPrefix, new.cellphone_number);
END

CREATE TRIGGER tbi_calls before insert on calls FOR EACH ROW
BEGIN
    declare set_price FLOAT;
    declare set_id_price int;
    declare set_id_city_origin int;
    declare set_id_city_destination int;
    IF (TIMESTAMPDIFF(MINUTE, new.start_time, new.end_time) < 0) then
        signal sqlstate '10001'
        SET MESSAGE_TEXT = 'Wrong date, the call can not have a negative munutes call',
        MYSQL_ERRNO = 2.2;
    END IF;
    IF (!Exists(select * from cellphones where cellphone_number = new.number_origin)
    or !Exists(select * from cellphones where cellphone_number = new.number_destination)
    or new.number_origin = new.number_destination) then
        signal sqlstate '10001'
        SET MESSAGE_TEXT = 'Wrong number ',
        MYSQL_ERRNO = 2.2;
    END IF;
    set new.id_cellphone_origin = (select id_cellphone from cellphones where cellphone_number = new.number_origin);
    set new.id_cellphone_destination = (select id_cellphone from cellphones where cellphone_number = new.number_destination);

    select u.id_city into set_id_city_origin from cellphones ce
    join users u on u.id_user = ce.id_user
    where ce.id_cellphone = new.id_cellphone_origin;

    select u.id_city into set_id_city_destination from cellphones ce
    join users u on u.id_user = ce.id_user
    where ce.id_cellphone = new.id_cellphone_destination;

    select price_per_minute, id_price into set_price, set_id_price  from prices where id_origin_city = set_id_city_origin and id_destination_city = set_id_city_destination;
    set new.final_value = set_price * TIMESTAMPDIFF(MINUTE, new.start_time, new.end_time);
    set new.id_price = set_id_price;
END

# 1 of month , execute method sp_invoicing() -> the calls that are not invoicing, generate an new bill with 15 days used.
CREATE PROCEDURE sp_invoicing()
begin
    declare stop_cursor_one, stop_cursor_two BOOLEAN default false;
    declare set_id_user int;
    declare set_id_cellphone int;
    declare set_total_price int;
    declare set_amount_calls int;
    declare set_id_bills int;
    declare set_id_call int;

    declare bill_cursor cursor for
    select ce.id_user, ce.id_cellphone, sum(ca.final_value) as total_price, count(ca.id_call) as amount_calls
    from cellphones ce inner join calls ca on ce.id_cellphone = ca.id_cellphone_origin
    where ca.id_bill is null
    group  by ce.id_cellphone;

    declare continue HANDLER for not found set  stop_cursor_one = true;
    open bill_cursor;
    set_Bills : LOOP
    fetch bill_cursor into set_id_user, set_id_cellphone, set_total_price, set_amount_calls;
        if stop_cursor_one THEN
            close bill_cursor;
            LEAVE set_Bills;
        END IF;
        #Insert bills for cellphones
        insert into bills
        (id_cellphone, id_user, amount_of_calls, final_price, bill_date, due_date)
        values (set_id_cellphone, set_id_user, set_amount_calls, set_total_price, now(), now() + interval 15 day);
        set set_id_bills = last_insert_id();

        BLOCK2: BEGIN
        #update call with the generated bill
        declare call_cursor cursor for
        select ca.id_call from calls ca
        where ca.id_cellphone_origin = set_id_cellphone;

        declare continue HANDLER for not found set  stop_cursor_two = true;
        open call_cursor;
        set_calls : LOOP
        fetch call_cursor into set_id_call;
            if stop_cursor_two THEN
                #when you itarates more than once you should restart the cursor.
                set stop_cursor_two = false;
                close call_cursor;
                LEAVE set_calls;
            END IF;
            update calls set id_bill = set_id_bills where id_call = set_id_call and id_bill is null;
            END LOOP set_calls;
        END BLOCK2;
    END LOOP set_Bills;
END

CREATE PROCEDURE debug_msg(enabled INTEGER, msg VARCHAR(255))
BEGIN
    IF enabled THEN
        select concat('** ', msg) AS '** DEBUG:';
    END IF;
END

CREATE EVENT IF NOT EXISTS sp_invoicing
    ON SCHEDULE EVERY '1' MONTH
STARTS '2020-06-1 00:00:00'
    ON COMPLETION PRESERVE
DO
    call sp_invoicing ();
