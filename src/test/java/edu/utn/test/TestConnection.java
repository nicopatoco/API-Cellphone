package edu.utn.test;

import edu.utn.dao.factory.MysqlDaoFactory;
import edu.utn.domain.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static edu.utn.dao.factory.MysqlDaoFactory.CLIENT_MYSQL_DAO;

public class TestConnection {
    @BeforeAll
    public static void setUp() throws IOException {
        Properties config = new Properties();
        config.load(new FileInputStream("./src/main/java/edu/utn/conf/app.properties"));
        MysqlDaoFactory mysqlDaoFactory = new MysqlDaoFactory(config);
    }

    @Test
    public void testGetUserById() {
        Client client = CLIENT_MYSQL_DAO.getById("35885684");
        Assertions.assertEquals("35885684", client.getId());
        Assertions.assertEquals("nico", client.getName());
        Assertions.assertEquals("Herrera", client.getSurname());
        Assertions.assertEquals(1, client.getCity());
    }
}
