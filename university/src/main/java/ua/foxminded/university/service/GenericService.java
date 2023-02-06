package ua.foxminded.university.service;

import java.util.List;

public interface GenericService <T> {
    void add(T t);
    void deleteById(int id);
    T getById(int id);
    List<T> getAll();
}
