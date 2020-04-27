package edu.utn.dao.mysql;

public class MysqlUtils {
    private static String BASE_CLIENT_QUERY = "select * from Clients ";
    protected static final String GET_CLIENT_BY_ID_QUERY = BASE_CLIENT_QUERY + "where id = ?";
}
