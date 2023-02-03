package ua.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.model.Course;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class CourseJdbcDAOTest {
    private static final Course expectedCourse1 = Course.builder().courseId(101).hours(36).build();
    private static final Course expectedCourse2 = Course.builder().courseId(102).hours(48).build();    
    private static final Course expectedCourse3 = Course.builder().courseId(103).hours(60).build();
    
    @Autowired
    private CourseJdbcDAO dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddCourse_whenAddMethodCalled() {
        dao.add(expectedCourse1);
        Course actualCourse = dao.getById(expectedCourse1.getCourseId());
        assertEquals(expectedCourse1, actualCourse);        
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteCourse_whenDeleteMethodCalled() {
        dao.add(expectedCourse1);
        dao.deleteById(expectedCourse1.getCourseId());
        Course actualCourse = dao.getById(expectedCourse1.getCourseId());
        assertNull(actualCourse);    
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetCourse_whenGetByIdMethodCalled() {
        dao.add(expectedCourse1);
        Course actualCourse = dao.getById(expectedCourse1.getCourseId());
        assertEquals(expectedCourse1, actualCourse);        
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGettAllCourses_whenGetAllMethodCalled() {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(expectedCourse1);
        expectedCourses.add(expectedCourse2);
        expectedCourses.add(expectedCourse3);
        dao.add(expectedCourse1);
        dao.add(expectedCourse2);
        dao.add(expectedCourse3);
        List<Course> actualCourses = dao.getAll();
        assertEquals(expectedCourses, actualCourses);        
    }
}
