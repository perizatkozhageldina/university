package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.TeacherJdbcDAO;
import ua.foxminded.university.model.Teacher;

@Service
public class TeacherService implements GenericService<Teacher> {

    @Autowired
    private TeacherJdbcDAO dao;
    
    public void add(Teacher teacher) {
        dao.add(teacher);
    }
    
    public void deleteById(int id) {
        dao.deleteById(id);
    }
    
    public Teacher getById(int id) {
        return dao.getById(id);
    }
    
    public List<Teacher> getAll() {
        return dao.getAll();
    }
}
