package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.DAOException;
import ua.foxminded.university.dao.GroupJdbcDAO;
import ua.foxminded.university.model.Group;

@Service
public class GroupService {    
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);
    private GroupJdbcDAO dao;

    @Autowired
    public GroupService(GroupJdbcDAO dao) {
        this.dao = dao;
    }

    public void add(Group group) {
        try {
            dao.add(group);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    
    public void update(Group group) {
        try {
            dao.update(group);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void deleteById(long id) {
        try {
            dao.deleteById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public Group getById(long id) {
        try {
            return dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Group> getAll() {
        try {
            return dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
