package ua.foxminded.university.dao;

import java.util.List;

public interface GenericDAO <T> {
    void add(T t);
    void deleteById(int id);
    T getById(int id);
    List<T> getAll();
}
