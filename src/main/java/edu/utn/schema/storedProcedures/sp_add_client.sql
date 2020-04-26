DELIMITER //
CREATE PROCEDURE ADD_CLIENT (IN cId varchar(50), IN cName varchar(50), IN cSurname varchar(50), In cCityId int, OUT newId int)
BEGIN
	IF ((select count(id) from Clients where id = cId) = 0) then 
		insert into Clients (id, name, surname, city_id) values (cId, cName, cSurname, cCityId);
		set newId = LAST_INSERT_ID();
	ELSE
		signal sqlstate '10001' 
		SET MESSAGE_TEXT = 'Client already exists', 
		MYSQL_ERRNO = 2.2;
	END IF;
END//
DELIMITER ;