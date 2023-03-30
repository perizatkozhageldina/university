package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.CourseJdbcDAO;
import ua.foxminded.university.dao.DAOException;
import ua.foxminded.university.model.Course;

@Service
public class CourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);
    private CourseJdbcDAO dao;

    @Autowired
    public CourseService(CourseJdbcDAO dao) {
        this.dao = dao;
    }

    @Transactional
    public boolean add(Course course) {
        try {
            dao.add(course);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return true;
        }
    }
    
    @Transactional
    public boolean update(Course course) {
        try {
            dao.update(course);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return true;
        }
    }

    @Transactional
    public boolean deleteById(long id) {
        try {
            dao.deleteById(id);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return true;
        }
    }

    @Transactional
    public Course getById(long id) {
        try {
            return dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }        
    }

    @Transactional
    public List<Course> getAll() {
        try {
            return dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}