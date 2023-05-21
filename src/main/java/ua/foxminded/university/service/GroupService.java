package ua.foxminded.university.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.repository.GroupJdbcRepository;
import ua.foxminded.university.repository.StudentJdbcRepository;

import javax.validation.Valid;

@Service
@Validated
public class GroupService {
    private GroupJdbcRepository dao;

    @Autowired
    public GroupService(GroupJdbcRepository dao) {
        this.dao = dao;
    }

    public void save(@Valid Group group) throws ServiceException {
        dao.save(group);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Group getById(long id) throws ServiceException {
        return dao.getReferenceById(id);
    }

    public List<Group> getAll() throws ServiceException {
        return dao.findAll();
    }
}