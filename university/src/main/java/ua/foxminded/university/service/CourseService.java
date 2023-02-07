package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.CourseJdbcDAO;
import ua.foxminded.university.model.Course;

@Service
public class CourseService implements GenericService<Course>{
    
    @Autowired
    private CourseJdbcDAO dao;
    
    public void add(Course course) {
        dao.add(course);
    }
    
    public void deleteById(int id) {
        dao.deleteById(id);
    }
    
    public Course getById(int id) {
        return dao.getById(id);
    }
    
    public List<Course> getAll() {
        return dao.getAll();
    }
}
