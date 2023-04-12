package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.model.Group;
import ua.foxminded.university.repository.RepositoryException;
import ua.foxminded.university.repository.GroupJdbcRepository;

@Service
public class GroupService {    
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);
    private GroupJdbcRepository dao;

    @Autowired
    public GroupService(GroupJdbcRepository dao) {
        this.dao = dao;
    }

    public boolean add(Group group) throws ServiceException {
        try {
            dao.add(group);
            return true;
        } catch (RepositoryException e) {            
            LOGGER.error(e.getMessage());
            return false;
        }
    }
    
    public boolean update(Group group) throws ServiceException {
        try {
            dao.update(group);
            return true;
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteById(long id) throws ServiceException {
        try {
            dao.deleteById(id);
            return true;
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public Group getById(long id) throws ServiceException {
        try {
            return dao.getById(id);
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Group> getAll() throws ServiceException {
        try {
            return dao.getAll();
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
