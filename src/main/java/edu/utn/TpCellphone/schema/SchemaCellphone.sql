CREATE DATABASE IF NOT EXISTS CellphoneCompany;

USE CellphoneCompany;

CREATE TABLE IF NOT EXISTS Provinces
(
    id_province INT AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id_province)
);

CREATE TABLE IF NOT EXISTS Cities
(
    id_city     INT AUTO_INCREMENT,
    id_province INT         NOT NULL,
    name        VARCHAR(50) NOT NULL UNIQUE,
    prefix      INT         NOT NULL UNIQUE,
    PRIMARY KEY (id_city),
    CONSTRAINT id_province_cities FOREIGN KEY (id_province) REFERENCES Provinces (id_province)
);

CREATE TABLE IF NOT EXISTS Users
(
    id_user   INT AUTO_INCREMENT,
    id        VARCHAR(50)              NOT NULL UNIQUE,
    name      VARCHAR(50)              NOT NULL,
    surname   VARCHAR(50)              NOT NULL,
    id_city   INT                      NOT NULL,
    username  VARCHAR(50)              NOT NULL UNIQUE,
    password  VARCHAR(50)              NOT NULL,
    user_type ENUM ("admin", "client") NOT NULL,
    PRIMARY KEY (id_user),
    CONSTRAINT id_city_user FOREIGN KEY (id_city) REFERENCES Cities (id_city)
);

CREATE TABLE IF NOT EXISTS Prices
(
    id_price            INT AUTO_INCREMENT,
    id_origin_city      INT   NOT NULL,
    id_destination_city INT   NOT NULL,
    price_per_minute    FLOAT NOT NULL,
    PRIMARY KEY (id_price),
    CONSTRAINT id_origin_city_prices FOREIGN KEY (id_origin_city) REFERENCES Cities (id_city),
    CONSTRAINT id_destination_city_prices FOREIGN KEY (id_destination_city) REFERENCES Cities (id_city)
);


CREATE TABLE IF NOT EXISTS Cellphones
(
    id_cellphone     INT AUTO_INCREMENT,
    cellphone_number VARCHAR(50)             NOT NULL UNIQUE,
    line_type        ENUM ("mobile", "home") NOT NULL,
    id_user          INT                     NOT NULL,
    PRIMARY KEY (id_cellphone),
    CONSTRAINT id_user_cellphones FOREIGN KEY (id_user) REFERENCES Users (id_user)
);

CREATE TABLE IF NOT EXISTS Bills
(
    id_bill         INT AUTO_INCREMENT,
    id_cellphone    INT  NOT NULL,
    id_user         INT  NOT NULL,
    amount_of_calls INT,
    final_price     FLOAT,
    bill_date       DATE NOT NULL,
    due_date        DATE NOT NULL,
    PRIMARY KEY (id_bill),
    CONSTRAINT id_cellphone_bills FOREIGN KEY (id_cellphone) REFERENCES Cellphones (id_cellphone),
    CONSTRAINT id_user_bills FOREIGN KEY (id_user) REFERENCES Users (id_user)
);

CREATE TABLE IF NOT EXISTS Calls
(
    id_call                  INT AUTO_INCREMENT,
    id_cellphone_origin      INT,
    id_cellphone_destination INT,
    id_price                 INT,
    id_bill                  INT,
    start_time               DATETIME    NOT NULL,
    end_time                 DATETIME    NOT NULL,
    duration                 INT,
    final_value              FLOAT,
    number_origin            varchar(50) NOT NULL,
    number_destination       varchar(50) NOT NULL,
    PRIMARY KEY (id_call),
    CONSTRAINT id_cellphone_origin_calls FOREIGN KEY (id_cellphone_origin) REFERENCES Cellphones (id_cellphone),
    CONSTRAINT id_cellphone_destination_calls FOREIGN KEY (id_cellphone_destination) REFERENCES Cellphones (id_cellphone),
    CONSTRAINT id_price_calls FOREIGN KEY (id_price) REFERENCES Prices (id_price),
    CONSTRAINT id_bill_calls FOREIGN KEY (id_bill) REFERENCES Bills (id_bill)
);

## TRIGGERS AND STORES PROCEDURES

DELIMITER //
CREATE TRIGGER tbi_cellphones
    BEFORE INSERT
    ON cellphones
    FOR EACH ROW
BEGIN
    DECLARE setPrefix INT;
    SET setPrefix = 0;
    SELECT prefix
    INTO setPrefix
    FROM users u
             JOIN cities c ON c.id_city = u.id_city
    WHERE id_user = new.id_user;

    SET new.cellphone_number = concat(setPrefix, new.cellphone_number);
END
//


DELIMITER $$
CREATE TRIGGER tbi_calls
    BEFORE INSERT
    ON calls
    FOR EACH ROW
BEGIN
    DECLARE set_price FLOAT;
    DECLARE set_id_price INT;
    DECLARE set_id_city_origin INT;
    DECLARE set_id_city_destination INT;

    IF (TIMESTAMPDIFF(MINUTE, new.start_time, new.end_time) < 0) THEN
        SIGNAL SQLSTATE '10001'
            SET MESSAGE_TEXT = 'Wrong date, the call can not have a negative munutes call',
                MYSQL_ERRNO = 2.2;
    END IF;
    IF (!Exists(SELECT * FROM cellphones WHERE cellphone_number = new.number_origin)
        OR !Exists(SELECT * FROM cellphones WHERE cellphone_number = new.number_destination)
        OR new.number_origin = new.number_destination) THEN
        SIGNAL SQLSTATE '10001'
            SET MESSAGE_TEXT = 'Wrong number ',
                MYSQL_ERRNO = 2.2;
    END IF;

    SET new.id_cellphone_origin = (SELECT id_cellphone FROM cellphones WHERE cellphone_number = new.number_origin);
    SET new.id_cellphone_destination =
            (SELECT id_cellphone FROM cellphones WHERE cellphone_number = new.number_destination);

    SELECT u.id_city
    INTO set_id_city_origin
    FROM cellphones ce
             JOIN users u ON u.id_user = ce.id_user
    WHERE ce.id_cellphone = new.id_cellphone_origin;

    SELECT u.id_city
    INTO set_id_city_destination
    FROM cellphones ce
             JOIN users u ON u.id_user = ce.id_user
    WHERE ce.id_cellphone = new.id_cellphone_destination;

    SELECT price_per_minute, id_price
    INTO set_price, set_id_price
    FROM prices
    WHERE id_origin_city = set_id_city_origin
      AND id_destination_city = set_id_city_destination;
    SET new.duration = TIMESTAMPDIFF(MINUTE, new.start_time, new.end_time);
    SET new.final_value = set_price * new.duration;
    SET new.id_price = set_id_price;
END
$$


# 1 of month , execute method sp_invoicing() -> the calls that are not invoicing, generate an new bill with 15 days used.
DELIMITER //
CREATE PROCEDURE sp_invoicing()
begin
    DECLARE stop_cursor_one, stop_cursor_two BOOLEAN DEFAULT FALSE;
    DECLARE set_id_user INT;
    DECLARE set_id_cellphone INT;
    DECLARE set_total_price INT;
    DECLARE set_amount_calls INT;
    DECLARE set_id_bills INT;
    DECLARE set_id_call INT;

    DECLARE bill_cursor CURSOR FOR
        SELECT ce.id_user, ce.id_cellphone, sum(ca.final_value) AS total_price, count(ca.id_call) AS amount_calls
        FROM cellphones ce
                 INNER JOIN calls ca ON ce.id_cellphone = ca.id_cellphone_origin
        WHERE ca.id_bill IS NULL
        GROUP BY ce.id_cellphone;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET stop_cursor_one = TRUE;
    OPEN bill_cursor;
    set_Bills :
    LOOP
        FETCH bill_cursor INTO set_id_user, set_id_cellphone, set_total_price, set_amount_calls;
        IF stop_cursor_one THEN
            CLOSE bill_cursor;
            LEAVE set_Bills;
        END IF;
        #Insert bills for cellphones
        INSERT INTO bills
        (id_cellphone, id_user, amount_of_calls, final_price, bill_date, due_date)
        VALUES (set_id_cellphone, set_id_user, set_amount_calls, set_total_price, now(), now() + INTERVAL 15 DAY);
        SET set_id_bills = last_insert_id();

        BLOCK2:
        BEGIN
            #update call with the generated bill
            DECLARE call_cursor CURSOR FOR
                SELECT ca.id_call
                FROM calls ca
                WHERE ca.id_cellphone_origin = set_id_cellphone;

            DECLARE CONTINUE HANDLER FOR NOT FOUND SET stop_cursor_two = TRUE;
            OPEN call_cursor;
            set_calls :
            LOOP
                FETCH call_cursor INTO set_id_call;
                IF stop_cursor_two THEN
                    #when you itarates more than once you should restart the cursor.
                    SET stop_cursor_two = FALSE;
                    CLOSE call_cursor;
                    LEAVE set_calls;
                END IF;
                update calls SET id_bill = set_id_bills WHERE id_call = set_id_call AND id_bill IS NULL;
            END LOOP set_calls;
        END BLOCK2;
    END LOOP set_Bills;
END
//


DELIMITER $$
CREATE PROCEDURE debug_msg(enabled INTEGER, msg VARCHAR(255))
BEGIN
    IF enabled THEN
        SELECT concat('** ', msg) AS '** DEBUG:';
    END IF;
END
$$

DELIMITER //
CREATE EVENT IF NOT EXISTS sp_invoicing
    ON SCHEDULE EVERY '1' MONTH
        STARTS '2020-06-1 00:00:00'
    ON COMPLETION PRESERVE
    DO
    call sp_invoicing();
//