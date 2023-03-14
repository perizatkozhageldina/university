package ua.foxminded.university.dao;

import java.util.List;

public interface GenericDAO <T> {
    void add(T t) throws DAOException;
    void update(T t) throws DAOException;
    void deleteById(long id) throws DAOException;
    T getById(long id) throws DAOException;
    List<T> getAll() throws DAOException;
}
