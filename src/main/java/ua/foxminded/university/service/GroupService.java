package ua.foxminded.university.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.model.Group;
import ua.foxminded.university.repository.GroupJdbcRepository;

@Service
public class GroupService {
    private GroupJdbcRepository dao;

    @Autowired
    public GroupService(GroupJdbcRepository dao) {
        this.dao = dao;
    }

    public void save(Group group) throws ServiceException {
        dao.save(group);
    }

    public void update(Group group) throws ServiceException {
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