package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.university.dao.StudentJdbcDAO;
import ua.foxminded.university.model.Student;

public class StudentService {

    @Autowired
    private StudentJdbcDAO dao;
    
    public void addStudent(Student student) {
        dao.add(student);
    }
    
    public void deleteStudent(Student student) {
        dao.deleteById(student.getStudentId());
    }
    
    public Student getStudentById(int id) {
        return dao.getById(id);
    }
    
    public List<Student> getAllStudents() {
        return dao.getAll();
    }
}
