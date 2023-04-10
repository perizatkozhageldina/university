package ua.foxminded.university.service;

import java.util.Collections;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
    private StudentJdbcDAO dao;

    @Autowired
    public StudentService(StudentJdbcDAO dao) {
        this.dao = dao;
    }

    public boolean add(Student student) {
        try {
            dao.add(student);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
    
    public boolean update(Student student) {
        try {
            dao.update(student);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteById(long id) {
        try {
            dao.deleteById(id);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public Student getById(long id) {
        try {
            return dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Student> getAll() {
        try {
            return dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
