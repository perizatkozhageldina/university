package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.university.dao.TeacherJdbcDAO;
import ua.foxminded.university.model.Teacher;

public class TeacherService {

    @Autowired
    private TeacherJdbcDAO dao;
    
    public void addTeacher(Teacher teacher) {
        dao.add(teacher);
    }
    
    public void deleteTeacher(Teacher teacher) {
        dao.deleteById(teacher.getTeacherId());
    }
    
    public Teacher getTeacherById(int id) {
        return dao.getById(id);
    }
    
    public List<Teacher> getAllTeachers() {
        return dao.getAll();
    }
}
