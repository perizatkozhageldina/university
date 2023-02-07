package ua.foxminded.university.service;

import java.util.List;

public interface GenericService <T> {
    void add(T t);
    void deleteById(long id);
    T getById(long id);
    List<T> getAll();
}
