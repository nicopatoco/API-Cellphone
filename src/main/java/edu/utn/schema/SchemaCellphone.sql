DROP DATABASE CellphoneCompany;

CREATE DATABASE IF NOT EXISTS CellphoneCompany;

USE CellphoneCompany;

CREATE TABLE IF NOT EXISTS Provinces (
	provinces_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Cities (
	city_id INT AUTO_INCREMENT PRIMARY KEY,
    id_province INT NOT NULL, 
    name VARCHAR(50) NOT NULL,
    prefix INT NOT NULL UNIQUE,
    CONSTRAINT id_province FOREIGN KEY(id_province) references Provinces(provinces_id)
);

CREATE TABLE IF NOT EXISTS Clients (
	client_id INT AUTO_INCREMENT PRIMARY KEY,
    id VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    city_id INT NOT NULL,
    CONSTRAINT city_id FOREIGN KEY(city_id) references Cities(city_id)
);

CREATE TABLE IF NOT EXISTS Prices (
	prices_id INT AUTO_INCREMENT PRIMARY KEY,
    id_origin_city INT NOT NULL,
    id_destination_city INT NOT NULL,
	price_per_minute FLOAT NOT NULL,
    CONSTRAINT id_origin_city FOREIGN KEY(id_origin_city) references Cities(city_id),
    CONSTRAINT id_destination_city FOREIGN KEY(id_destination_city) references Cities(city_id)
);

CREATE TABLE IF NOT EXISTS Cellphones (
	cellphone_id INT AUTO_INCREMENT PRIMARY KEY,
    cellphone_number INT NOT NULL,
    user_type VARCHAR(50) NOT NULL,
	client_id INT NOT NULL,
    CONSTRAINT client_id FOREIGN KEY(client_id) references Clients(client_id)
);

CREATE TABLE IF NOT EXISTS Bills (
	bill_id INT AUTO_INCREMENT PRIMARY KEY,
    cellphone_id INT NOT NULL,
    client_id INT NOT NULL,
    amount_of_calls INT,
    final_price FLOAT,
    bill_date DATETIME NOT NULL,
    due_date DATETIME NOT NULL,
    CONSTRAINT cellphone_id FOREIGN KEY(cellphone_id) references Cellphones(cellphone_id),
    CONSTRAINT client_id FOREIGN KEY(client_id) references Clients(client_id)
);

CREATE TABLE IF NOT EXISTS Calls (
	call_id INT AUTO_INCREMENT PRIMARY KEY,
    cellphone_id_origin INT NOT NULL,
    cellphone_id_destination INT NOT NULL,
    price_id INT NOT NULL,
    bill_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    final_value FLOAT,
    CONSTRAINT cellphone_id_origin FOREIGN KEY(cellphone_id_origin) references Cellphones(cellphone_id),
    CONSTRAINT cellphone_id_destination FOREIGN KEY(cellphone_id_destination) references Cellphones(cellphone_id),
    CONSTRAINT price_id FOREIGN KEY(price_id) references Prices(price_id),
    CONSTRAINT bill_id FOREIGN KEY(bill_id) references Bills(bill_id)
);