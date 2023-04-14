package ua.foxminded.university.repository;

import java.util.List;

public interface GenericRepository<T> {
    void add(T t) throws RepositoryException;

    void update(T t) throws RepositoryException;

    void deleteById(long id) throws RepositoryException;

    T getById(long id) throws RepositoryException;

    List<T> getAll() throws RepositoryException;
}