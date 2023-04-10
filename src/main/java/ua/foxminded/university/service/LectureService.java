package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.DAOException;
import ua.foxminded.university.dao.LectureJdbcDAO;
import ua.foxminded.university.model.Lecture;

@Service
public class LectureService {    
    private static final Logger LOGGER = LoggerFactory.getLogger(LectureService.class);
    private LectureJdbcDAO dao;

    @Autowired
    public LectureService(LectureJdbcDAO dao) {
        this.dao = dao;
    }

    public boolean add(Lecture lecture) {
        try {
            dao.add(lecture);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
    
    public boolean update(Lecture lecture) {
        try {
            dao.update(lecture);
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

    public Lecture getById(long id) {
        try {
            return dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Lecture> getAll() {
        try {
            return dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
