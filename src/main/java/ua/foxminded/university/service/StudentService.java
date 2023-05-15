package ua.foxminded.university.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.repository.StudentJdbcRepository;

import javax.validation.Valid;

@Service
@Validated
public class StudentService {
    private StudentJdbcRepository dao;

    @Autowired
    public StudentService(StudentJdbcRepository dao) {
        this.dao = dao;
    }

    public void save(@Valid Student student) throws ServiceException {
        dao.save(student);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Student getById(long id) throws ServiceException {
        return dao.getReferenceById(id);
    }

    public List<Student> getAll() throws ServiceException {
        return dao.findAll();
    }
}