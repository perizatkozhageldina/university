package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.university.dao.GroupJdbcDAO;
import ua.foxminded.university.model.Group;

public class GroupService {

    @Autowired
    private GroupJdbcDAO dao;
    
    public void addCourse(Group group) {
        dao.add(group);
    }
    
    public void deleteGroup(Group group) {
        dao.deleteById(group.getGroupId());
    }
    
    public Group getGroupById(int id) {
        return dao.getById(id);
    }
    
    public List<Group> getAllGroups() {
        return dao.getAll();
    }
}
