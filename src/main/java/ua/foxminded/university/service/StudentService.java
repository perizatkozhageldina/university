package ua.foxminded.university.service;

import java.util.List;
import java.util.Optional;

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

    public Student save(@Valid Student student) throws ServiceException {
        return dao.save(student);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Student getById(long id) throws ServiceException {
        Optional<Student> studentOptional = dao.findById(id);
        return studentOptional.orElseThrow(() -> new ServiceException("Student not found with ID: " + id));
    }

    public List<Student> getAll() throws ServiceException {
        return dao.findAll();
    }
}