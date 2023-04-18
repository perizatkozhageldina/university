package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.model.Course;
import ua.foxminded.university.repository.CourseJdbcRepository;
import ua.foxminded.university.repository.RepositoryException;

@Service
public class CourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);
    private CourseJdbcRepository dao;

    @Autowired
    public CourseService(CourseJdbcRepository dao) {
        this.dao = dao;
    }

    public boolean add(Course course) throws ServiceException {
        try {
            dao.add(course);
            return true;
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean update(Course course) throws ServiceException {
        try {
            dao.update(course);
            return true;
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteById(long id) throws ServiceException {
        try {
            dao.deleteById(id);
            return true;
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public Course getById(long id) throws ServiceException {
        try {
            return dao.getById(id);
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Course> getAll() throws ServiceException {
        try {
            return dao.getAll();
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}