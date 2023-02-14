package ua.foxminded.university.service;

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
    private LectureJdbcDAO dao;
    private static final Logger LOGGER = LoggerFactory.getLogger(LectureService.class);

    @Autowired
    public LectureService(LectureJdbcDAO dao) {
        this.dao = dao;
    }

    public void add(Lecture lecture) {
        try {
            dao.add(lecture);
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

    public Lecture getById(long id) {
        Lecture lecture = null;
        try {
            lecture = dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
        return lecture;
    }

    public List<Lecture> getAll() {
        List<Lecture> lectures = null;
        try {
            lectures = dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
        return lectures;
    }
}
