package ua.foxminded.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.CourseJdbcDAO;
import ua.foxminded.university.dao.DAOException;
import ua.foxminded.university.model.Course;

@Service
public class CourseService {
    private CourseJdbcDAO dao;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    public CourseService(CourseJdbcDAO dao) {
        this.dao = dao;
    }

    public void add(Course course) {
        try {
            dao.add(course);
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

    public Course getById(long id) {
        Course course = null;
        try {
            course = dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
        return course;
    }

    public List<Course> getAll() {
        List<Course> courses = null;
        try {
            courses = dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }        
        return courses;
    }
}
