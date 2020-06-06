CREATE DATABASE IF NOT EXISTS CellphoneCompany;

USE CellphoneCompany;

CREATE TABLE IF NOT EXISTS Provinces(
    id_province INT AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY(id_province)
);

CREATE TABLE IF NOT EXISTS Cities (
    id_city INT AUTO_INCREMENT,
    id_province INT NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE,
    prefix INT NOT NULL UNIQUE,
    PRIMARY KEY (id_city), CONSTRAINT id_province_cities FOREIGN KEY(id_province)references Provinces(id_province)
);

CREATE TABLE IF NOT EXISTS Users(
    id_user   INT AUTO_INCREMENT,
    id        VARCHAR(50) NOT NULL UNIQUE,
    name      VARCHAR(50) NOT NULL,
    surname   VARCHAR(50) NOT NULL,
    id_city   INT NOT NULL,
    username  VARCHAR(50) NOT NULL UNIQUE,
    password  VARCHAR(50) NOT NULL,
    user_type ENUM("admin", "client")NOT NULL,
    PRIMARY KEY(id_user),
    CONSTRAINT id_city_user FOREIGN KEY(id_city)references Cities(id_city)
);

CREATE TABLE IF NOT EXISTS Prices (
    id_price INT AUTO_INCREMENT,
    id_origin_city INT NOT NULL,
    id_destination_city INT NOT NULL,
	  price_per_minute FLOAT NOT NULL,
    PRIMARY KEY (id_price), CONSTRAINT id_origin_city_prices FOREIGN KEY(id_origin_city)references Cities(id_city),
    CONSTRAINT id_destination_city_prices FOREIGN KEY(id_destination_city)references Cities(id_city)
);

CREATE TABLE IF NOT EXISTS Cellphones(
    id_cellphone     INT AUTO_INCREMENT,
    cellphone_number VARCHAR(50) NOT NULL UNIQUE,
    line_type        ENUM("mobile", "home")NOT NULL,
    id_user INT NOT NULL,
    PRIMARY KEY(id_cellphone),
    CONSTRAINT id_user_cellphones FOREIGN KEY(id_user)references Users(id_user)
);

CREATE TABLE IF NOT EXISTS Bills (
    id_bill INT AUTO_INCREMENT,
    id_cellphone INT NOT NULL,
    id_user INT NOT NULL,
    amount_of_calls INT,
    final_price FLOAT,
    bill_date DATE NOT NULL,
    due_date DATE NOT NULL,
    PRIMARY KEY (id_bill), CONSTRAINT id_cellphone_bills FOREIGN KEY(id_cellphone)references Cellphones(id_cellphone),
    CONSTRAINT id_user_bills FOREIGN KEY(id_user)references Users(id_user)
);

CREATE TABLE IF NOT EXISTS Calls (
    id_call INT AUTO_INCREMENT,
    id_cellphone_origin INT NOT NULL,
    id_cellphone_destination INT NOT NULL,
    id_price INT NOT NULL,
    id_bill INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    final_value FLOAT,
    PRIMARY KEY (id_call), CONSTRAINT id_cellphone_origin_calls FOREIGN KEY(id_cellphone_origin)references Cellphones(id_cellphone),
    CONSTRAINT id_cellphone_destination_calls FOREIGN KEY(id_cellphone_destination)references Cellphones(id_cellphone),
    CONSTRAINT id_price_calls FOREIGN KEY(id_price)references Prices(id_price),
    CONSTRAINT id_bill_calls FOREIGN KEY(id_bill)references Bills(id_bill)
);

//DELIMITER
CREATE TRIGGER tbi_cellphones before insert on cellphones FOR EACH ROW
BEGIN
    declare setPrefix int;
    set setPrefix = 0;
    select prefix into setPrefix from users u join cities c on c.id_city = u.id_city where id_user = new.id_user;
    set new.cellphone_number = concat(setPrefix, new.cellphone_number);
END//
DELIMITER;

