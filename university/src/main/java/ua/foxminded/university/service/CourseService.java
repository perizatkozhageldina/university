package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.university.dao.CourseJdbcDAO;
import ua.foxminded.university.model.Course;

public class CourseService {
    
    @Autowired
    private CourseJdbcDAO dao;
    
    public void addCourse(Course course) {
        dao.add(course);
    }
    
    public void deleteCourse(Course course) {
        dao.deleteById(course.getCourseId());
    }
    
    public Course getCourseById(int id) {
        return dao.getById(id);
    }
    
    public List<Course> getAllCourses() {
        return dao.getAll();
    }
}
