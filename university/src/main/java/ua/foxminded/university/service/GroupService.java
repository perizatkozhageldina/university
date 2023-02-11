package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.GroupJdbcDAO;
import ua.foxminded.university.model.Group;

@Service
public class GroupService {
    private GroupJdbcDAO dao;

    @Autowired
    public GroupService(GroupJdbcDAO dao) {
        this.dao = dao;
    }
    
    
    public void add(Group group) {
        dao.add(group);
    }
    
    public void deleteById(long id) {
        dao.deleteById(id);
    }
    
    public Group getById(long id) {
        return dao.getById(id);
    }
    
    public List<Group> getAll() {
        return dao.getAll();
    }
}
