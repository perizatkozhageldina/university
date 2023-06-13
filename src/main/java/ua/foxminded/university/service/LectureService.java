package ua.foxminded.university.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.repository.LectureJdbcRepository;

import javax.validation.Valid;

@Service
@Validated
public class LectureService {
    private LectureJdbcRepository dao;

    @Autowired
    public LectureService(LectureJdbcRepository dao) {
        this.dao = dao;
    }

    public Lecture save(@Valid Lecture lecture) throws ServiceException {
        return dao.save(lecture);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Lecture getById(long id) throws ServiceException  {
        return dao.findById(id).orElseThrow(() -> new ServiceException("Lecture not found with ID: " + id));
    }

    public List<Lecture> getAll() throws ServiceException {
        return dao.findAll();
    }
}