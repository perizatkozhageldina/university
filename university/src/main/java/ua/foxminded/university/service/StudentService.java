package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.StudentJdbcDAO;
import ua.foxminded.university.model.Student;

@Service
public class StudentService {
    private StudentJdbcDAO dao;

    @Autowired
    public StudentService(StudentJdbcDAO dao) {
        this.dao = dao;
    }
    
    public void add(Student student) {
        dao.add(student);
    }
    
    public void deleteById(long id) {
        dao.deleteById(id);
    }
    
    public Student getById(long id) {
        return dao.getById(id);
    }
    
    public List<Student> getAll() {
        return dao.getAll();
    }
}
