package ua.foxminded.university.service;

import java.util.List;

import javax.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.repository.CourseJdbcRepository;

@Service
@Validated
public class CourseService {
    private CourseJdbcRepository dao;

    @Autowired
    public CourseService(CourseJdbcRepository dao) {
        this.dao = dao;
    }

    public void save(@Valid Course course) throws ServiceException {
        dao.save(course);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Course getById(long id) throws ServiceException {
        return dao.getReferenceById(id);
    }

    public List<Course> getAll() throws ServiceException {
        return dao.findAll();
    }
}