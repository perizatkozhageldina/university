package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.university.dao.StudentJdbcDAO;
import ua.foxminded.university.model.Student;

public class StudentService implements GenericService<Student> {

    @Autowired
    private StudentJdbcDAO dao;
    
    public void add(Student student) {
        dao.add(student);
    }
    
    public void deleteById(int id) {
        dao.deleteById(id);
    }
    
    public Student getById(int id) {
        return dao.getById(id);
    }
    
    public List<Student> getAll() {
        return dao.getAll();
    }
}
