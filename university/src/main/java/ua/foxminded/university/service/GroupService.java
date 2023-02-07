package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.foxminded.university.dao.GroupJdbcDAO;
import ua.foxminded.university.model.Group;

@Component
public class GroupService implements GenericService<Group> {

    @Autowired
    private GroupJdbcDAO dao;
    
    public void add(Group group) {
        dao.add(group);
    }
    
    public void deleteById(int id) {
        dao.deleteById(id);
    }
    
    public Group getById(int id) {
        return dao.getById(id);
    }
    
    public List<Group> getAll() {
        return dao.getAll();
    }
}
