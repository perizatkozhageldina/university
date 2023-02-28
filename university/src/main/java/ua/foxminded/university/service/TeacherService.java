package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.DAOException;
import ua.foxminded.university.dao.TeacherJdbcDAO;
import ua.foxminded.university.model.Teacher;

@Service
public class TeacherService {    
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherService.class);
    private TeacherJdbcDAO dao;

    @Autowired
    public TeacherService(TeacherJdbcDAO dao) {
        this.dao = dao;
    }

    public void add(Teacher teacher) {
        try {
            dao.add(teacher);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    
    public void update(Teacher teacher) {
        try {
            dao.update(teacher);
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

    public Teacher getById(long id) {
        try {
            return dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Teacher> getAll() {
        try {
            return dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
