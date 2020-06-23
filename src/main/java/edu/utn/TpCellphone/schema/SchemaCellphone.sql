#DROP DATABASE IF EXISTS CellphoneCompany;
CREATE DATABASE IF NOT EXISTS CellphoneCompany;

USE CellphoneCompany;

CREATE TABLE IF NOT EXISTS Provinces
(
    id_province INT(10) AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id_province)
) CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Cities
(
    id_city     INT(10) AUTO_INCREMENT,
    id_province INT(10)      NOT NULL,
    name        VARCHAR(255) NOT NULL UNIQUE,
    prefix      INT(4)       NOT NULL UNIQUE,
    PRIMARY KEY (id_city),
    CONSTRAINT id_province_cities FOREIGN KEY (id_province) REFERENCES Provinces (id_province)
) CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Users
(
    id_user   INT(10) AUTO_INCREMENT,
    id        VARCHAR(20)                         NOT NULL UNIQUE,
    name      VARCHAR(32)                         NOT NULL,
    surname   VARCHAR(32)                         NOT NULL,
    username  VARCHAR(40)                         NOT NULL UNIQUE,
    password  VARCHAR(255)                        NOT NULL,
    user_type ENUM ("admin", "client", "antenna") NOT NULL,
    id_city   INT(10)                             NOT NULL,
    PRIMARY KEY (id_user),
    CONSTRAINT id_city_user FOREIGN KEY (id_city) REFERENCES Cities (id_city)
) CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Prices
(
    id_price            INT(10) AUTO_INCREMENT,
    id_origin_city      INT(10) NOT NULL,
    id_destination_city INT(10) NOT NULL,
    price_per_minute    FLOAT   NOT NULL,
    PRIMARY KEY (id_price),
    CONSTRAINT id_origin_city_prices FOREIGN KEY (id_origin_city) REFERENCES Cities (id_city),
    CONSTRAINT id_destination_city_prices FOREIGN KEY (id_destination_city) REFERENCES Cities (id_city)
) CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Cellphones
(
    id_cellphone     INT(10) AUTO_INCREMENT,
    cellphone_number VARCHAR(15)             NOT NULL UNIQUE,
    line_type        ENUM ("mobile", "home") NOT NULL,
    status           INT(1) DEFAULT 1        NOT NULL,
    id_user          INT(10)                 NOT NULL,
    PRIMARY KEY (id_cellphone),
    CONSTRAINT id_user_cellphones FOREIGN KEY (id_user) REFERENCES Users (id_user)
) CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Bills
(
    id_bill         INT(10) AUTO_INCREMENT,
    id_cellphone    INT(10) NOT NULL,
    id_user         INT(10) NOT NULL,
    amount_of_calls INT(10),
    final_price     FLOAT,
    bill_date       DATE    NOT NULL,
    due_date        DATE    NOT NULL,
    PRIMARY KEY (id_bill),
    CONSTRAINT id_cellphone_bills FOREIGN KEY (id_cellphone) REFERENCES Cellphones (id_cellphone),
    CONSTRAINT id_user_bills FOREIGN KEY (id_user) REFERENCES Users (id_user)
) CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Calls
(
    id_call                  INT(10) AUTO_INCREMENT,
    id_cellphone_origin      INT(10),
    id_cellphone_destination INT(10),
    id_price                 INT(10),
    id_bill                  INT(10),
    start_time               DATETIME    NOT NULL,
    end_time                 DATETIME    NOT NULL,
    duration                 INT(10),
    final_value              FLOAT,
    number_origin            VARCHAR(15) NOT NULL,
    number_destination       VARCHAR(15) NOT NULL,
    PRIMARY KEY (id_call),
    CONSTRAINT id_cellphone_origin_calls FOREIGN KEY (id_cellphone_origin) REFERENCES Cellphones (id_cellphone),
    CONSTRAINT id_cellphone_destination_calls FOREIGN KEY (id_cellphone_destination) REFERENCES Cellphones (id_cellphone),
    CONSTRAINT id_price_calls FOREIGN KEY (id_price) REFERENCES Prices (id_price),
    CONSTRAINT id_bill_calls FOREIGN KEY (id_bill) REFERENCES Bills (id_bill)
) CHARSET = utf8;

CREATE INDEX idx_start_time USING BTREE ON CALLS (start_time);
CREATE INDEX idx_end_time USING BTREE ON CALLS (end_time);

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
    DECLARE count1 INT;
    DECLARE count2 INT;

    IF (TIMESTAMPDIFF(MINUTE, new.start_time, new.end_time) < 0) THEN
        SIGNAL SQLSTATE '10001'
            SET MESSAGE_TEXT = 'Wrong date, the call can not have a negative minutes call',
                MYSQL_ERRNO = 2.2;
    END IF;

    SET count1 = (SELECT COUNT(*) FROM cellphones WHERE cellphone_number = new.number_origin);
    SET count2 = (SELECT COUNT(*) FROM cellphones WHERE cellphone_number = new.number_destination);

    IF (count1 <= 0)
        OR (count2 <= 0) OR (new.number_origin = new.number_destination) THEN
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
        INSERT INTO stringg(message) VALUES (msg);
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

DELIMITER $$
CREATE PROCEDURE sp_insert_calls_massive()
set_calls :
BEGIN
    DECLARE x INT;
    SET x = 1;
    WHILE x <= 5
        DO
            insert into calls (number_origin, number_destination, start_time, end_time)
            SELECT o.cellphone_number, d.cellphone_number, NOW() - INTERVAL FLOOR(RAND() * 14) DAY, NOW()
            FROM cellphones as o,
                 cellphones d
            WHERE o.id_cellphone < 50
              AND d.id_cellphone > 50;
            SET x = x + 1;
        END WHILE;
END $$

DELIMITER //
CREATE PROCEDURE sp_get_calls()
BEGIN
    SELECT ca.number_origin      as numero_origen,
           ci1.name              as ciudad_origen,
           ca.number_destination as numero_destino,
           ci2.name              as ciudad_destino,
           final_value           as precio,
           duration              as duracion,
           start_time            as fecha
    FROM calls as ca
             INNER JOIN Cellphones as ce1 ON ca.id_cellphone_origin = ce1.id_cellphone
             INNER JOIN Cellphones as ce2 ON ca.id_cellphone_destination = ce2.id_cellphone
             INNER JOIN Users as us1 ON us1.id_user = ce1.id_user
             INNER JOIN Users as us2 ON us1.id_user = ce2.id_user
             INNER JOIN Cities as ci1 ON ci1.id_city = us1.id_city
             INNER JOIN Cities as ci2 ON ci2.id_city = us2.id_city;
END //

## JORGE
LOAD DATA INFILE 'D:\Google Drive\\UTN\\TUSI\\1ER CUATRIMESTRE\\TP FINAL\\TpCellphone\\src\\main\\java\\edu\\utn\\TpCellphone\\data\\provinces.csv' INTO TABLE provinces FIELDS TERMINATED BY ',' (name);
LOAD DATA INFILE 'D:\Google Drive\\UTN\\TUSI\\1ER CUATRIMESTRE\\TP FINAL\\TpCellphone\\src\\main\\java\\edu\\utn\\TpCellphone\\data\\cities.csv' INTO TABLE cities FIELDS TERMINATED BY ',' (id_province, name, prefix);
INSERT INTO users(id, name, surname, username, password, user_type, id_city)
VALUES ('00000000', 'antenna', 'antenna', 'antenna', 'F212100E38F782E152EBFAB712A0E6EC', 'antenna', 1);
INSERT INTO prices (id_origin_city, id_destination_city, price_per_minute)
SELECT o.id_city, d.id_city, RAND()
FROM cities as o,
     cities d;
LOAD DATA INFILE 'D:\Google Drive\\UTN\\TUSI\\1ER CUATRIMESTRE\\TP FINAL\\TpCellphone\\src\\main\\java\\edu\\utn\\TpCellphone\\data\\users.csv' INTO TABLE users FIELDS TERMINATED BY ',' (id, name, surname, username, password, user_type, id_city);
LOAD DATA INFILE 'D:\Google Drive\\UTN\\TUSI\\1ER CUATRIMESTRE\\TP FINAL\\TpCellphone\\src\\main\\java\\edu\\utn\\TpCellphone\\data\\cellphones.csv' INTO TABLE cellphones FIELDS TERMINATED BY ',' (cellphone_number, line_type, id_user);
call sp_insert_calls_massive();

## NICO
LOAD DATA INFILE '/Users/np.herrera/Documents/Facu/Programacion avanzada/TpCellphone/src/main/java/edu/utn/TpCellphone/data/provinces.csv' INTO TABLE provinces FIELDS TERMINATED BY ',' (name);
LOAD DATA INFILE '/Users/np.herrera/Documents/Facu/Programacion avanzada/TpCellphone/src/main/java/edu/utn/TpCellphone/data/cities.csv' INTO TABLE cities FIELDS TERMINATED BY ',' (id_province, name, prefix);
INSERT INTO users(id, name, surname, username, password, user_type, id_city)
VALUES ('00000000', 'antenna', 'antenna', 'antenna', 'F212100E38F782E152EBFAB712A0E6EC', 'antenna', 1);
INSERT INTO prices (id_origin_city, id_destination_city, price_per_minute)
SELECT o.id_city, d.id_city, RAND() FROM cities as o, cities d;
LOAD DATA INFILE '/Users/np.herrera/Documents/Facu/Programacion avanzada/TpCellphone/src/main/java/edu/utn/TpCellphone/data/users.csv' INTO TABLE users FIELDS TERMINATED BY ',' (id, name, surname, username, password, user_type, id_city);
LOAD DATA INFILE '/Users/np.herrera/Documents/Facu/Programacion avanzada/TpCellphone/src/main/java/edu/utn/TpCellphone/data/cellphones.csv' INTO TABLE cellphones FIELDS TERMINATED BY ',' (cellphone_number, line_type, id_user);
call sp_insert_calls_massive();