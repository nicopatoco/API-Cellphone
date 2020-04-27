package edu.utn.dao.factory;

import edu.utn.dao.ClientDaoInterface;
import edu.utn.dao.mysql.ClientMysqlDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlDaoFactory extends AbstractDaoFactory {
    public static ClientMysqlDao CLIENT_MYSQL_DAO;

    public MysqlDaoFactory(Properties config) {
        super(config);
        getClientDao();
    }

    public ClientDaoInterface getClientDao() {
        try {
            if (CLIENT_MYSQL_DAO == null) {
                String username = config.getProperty("db.user");
                String password = config.getProperty("db.password");
                String db = config.getProperty("db.name");

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + username + "&password=" + password + "");
                CLIENT_MYSQL_DAO = new ClientMysqlDao(conn);
            }
            return CLIENT_MYSQL_DAO;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
