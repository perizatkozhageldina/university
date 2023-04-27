package ua.foxminded.university.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.repository.TeacherJdbcRepository;

@Service
public class TeacherService {
    private TeacherJdbcRepository dao;

    @Autowired
    public TeacherService(TeacherJdbcRepository dao) {
        this.dao = dao;
    }

    public void save(Teacher teacher) throws ServiceException {
        dao.save(teacher);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Teacher getById(long id) throws ServiceException {
        return dao.findById(id).get();
    }

    public List<Teacher> getAll() throws ServiceException {
        return dao.findAll();
    }
}