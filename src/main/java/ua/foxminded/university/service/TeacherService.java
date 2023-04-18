package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.repository.RepositoryException;
import ua.foxminded.university.repository.TeacherJdbcRepository;

@Service
public class TeacherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherService.class);
    private TeacherJdbcRepository dao;

    @Autowired
    public TeacherService(TeacherJdbcRepository dao) {
        this.dao = dao;
    }

    public boolean add(Teacher teacher) throws ServiceException {
        try {
            dao.add(teacher);
            return true;
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean update(Teacher teacher) throws ServiceException {
        try {
            dao.update(teacher);
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

    public Teacher getById(long id) throws ServiceException {
        try {
            return dao.getById(id);
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Teacher> getAll() throws ServiceException {
        try {
            return dao.getAll();
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}