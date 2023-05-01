package ua.foxminded.university.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.repository.LectureJdbcRepository;

@Service
public class LectureService {
    private LectureJdbcRepository dao;

    @Autowired
    public LectureService(LectureJdbcRepository dao) {
        this.dao = dao;
    }

    public void save(Lecture lecture) throws ServiceException {
        dao.save(lecture);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Lecture getById(long id) throws ServiceException {
        return dao.getReferenceById(id);
    }

    public List<Lecture> getAll() throws ServiceException {
        return dao.findAll();
    }
}