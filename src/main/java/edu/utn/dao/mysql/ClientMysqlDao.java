package edu.utn.dao.mysql;

import edu.utn.dao.ClientDaoInterface;
import edu.utn.domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.utn.dao.mysql.MysqlUtils.GET_CLIENT_BY_ID_QUERY;

public class ClientMysqlDao implements ClientDaoInterface {
    Connection connection;

    public ClientMysqlDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client getById(String id) {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_CLIENT_BY_ID_QUERY);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            Client client = null;
            if (rs.next()) {
                client = createClient(rs);
            }
            rs.close();
            ps.close();
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error, getting client", e);
        }
    }

    private Client createClient(ResultSet rs) throws SQLException {
        Client client;
        client = new Client(rs.getString("id"), rs.getString("name"), rs.getString("surname"), rs.getInt("id_city"));
        return client;
    }

    @Override
    public Client add(Client value) throws SQLException {
        return null;
    }

    @Override
    public Client update(Client value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public Client getById(Integer id) {
        return null;
    }

    @Override
    public List<Client> getAll() {
        return null;
    }
}
