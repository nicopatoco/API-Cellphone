package edu.utn.dao;

import java.sql.SQLException;
import java.util.List;

public interface DaoInterface<T> {
    T add(T value) throws SQLException;

    T update(T value);

    void remove(Integer id);

    void remove(T value);

    T getById(Integer id);

    List<T> getAll();
}
