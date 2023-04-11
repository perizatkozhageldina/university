package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
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

    public boolean add(Teacher teacher) throws ServiceException {
        try {
            dao.add(teacher);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
    
    public boolean update(Teacher teacher) throws ServiceException {
        try {
            dao.update(teacher);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteById(long id) throws ServiceException {
        try {
            dao.deleteById(id);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public Teacher getById(long id) throws ServiceException {
        try {
            return dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Teacher> getAll() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
