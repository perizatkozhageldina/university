package ua.foxminded.university.dao;

import java.util.List;

public interface GenericDAO <T> {
    void add(T t);
    void deleteById(long id);
    T getById(long id);
    List<T> getAll();
}
