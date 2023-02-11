package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.TeacherJdbcDAO;
import ua.foxminded.university.model.Teacher;

@Service
public class TeacherService {
    private TeacherJdbcDAO dao;

    @Autowired
    public TeacherService(TeacherJdbcDAO dao) {
        this.dao = dao;
    }
    
    public void add(Teacher teacher) {
        dao.add(teacher);
    }
    
    public void deleteById(long id) {
        dao.deleteById(id);
    }
    
    public Teacher getById(long id) {
        return dao.getById(id);
    }
    
    public List<Teacher> getAll() {
        return dao.getAll();
    }
}
