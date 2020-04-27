package edu.utn.dao.factory;

import edu.utn.dao.ClientDaoInterface;

import java.util.Properties;

public abstract class AbstractDaoFactory {
    Properties config;

    public AbstractDaoFactory(Properties config) {
        this.config = config;
    }

    public abstract ClientDaoInterface getClientDao();
}
