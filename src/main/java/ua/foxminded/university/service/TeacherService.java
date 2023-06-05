package ua.foxminded.university.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.repository.TeacherJdbcRepository;

import javax.validation.Valid;

@Service
@Validated
public class TeacherService {
    private TeacherJdbcRepository dao;

    @Autowired
    public TeacherService(TeacherJdbcRepository dao) {
        this.dao = dao;
    }

    public Teacher save(@Valid Teacher teacher) throws ServiceException {
        return dao.save(teacher);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Optional<Teacher> getById(long id) throws ServiceException {
        return dao.findById(id);
    }

    public List<Teacher> getAll() throws ServiceException {
        return dao.findAll();
    }
}