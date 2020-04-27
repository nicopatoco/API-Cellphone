package edu.utn.dao;

import edu.utn.domain.Client;

public interface ClientDaoInterface extends DaoInterface<Client> {
    Client getById(String id);
}
