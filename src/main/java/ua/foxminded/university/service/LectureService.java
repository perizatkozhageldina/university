package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.repository.RepositoryException;
import ua.foxminded.university.repository.LectureJdbcRepository;

@Service
public class LectureService {    
    private static final Logger LOGGER = LoggerFactory.getLogger(LectureService.class);
    private LectureJdbcRepository dao;

    @Autowired
    public LectureService(LectureJdbcRepository dao) {
        this.dao = dao;
    }

    public boolean add(Lecture lecture) throws ServiceException {
        try {
            dao.add(lecture);
            return true;
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
    
    public boolean update(Lecture lecture) throws ServiceException {
        try {
            dao.update(lecture);
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

    public Lecture getById(long id) throws ServiceException {
        try {
            return dao.getById(id);
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Lecture> getAll() throws ServiceException {
        try {
            return dao.getAll();
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
