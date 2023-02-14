package ua.foxminded.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.DAOException;
import ua.foxminded.university.dao.StudentJdbcDAO;
import ua.foxminded.university.model.Student;

@Service
public class StudentService {
    private StudentJdbcDAO dao;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentJdbcDAO dao) {
        this.dao = dao;
    }

    public void add(Student student) {
        try {
            dao.add(student);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void deleteById(long id) {
        try {
            dao.deleteById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public Student getById(long id) {
        Student student = null;
        try {
            student = dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
        return student;
    }

    public List<Student> getAll() {
        List<Student> students = null;
        try {
            students = dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
        return students;
    }
}
