package ua.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.university.model.Course;

@ContextConfiguration(classes = CourseJdbcDAO.class) 
class CourseJdbcDAOTest {

    @Autowired
    CourseJdbcDAO dao;
    
    @Test
    @Sql("src/test/resources/testSchema.sql")
    void test() {
        Course course = Course.builder().id(7).creditHours(777).build();
        dao.add(course);
        Course ccc = dao.getById(course.getId());
        assertEquals(course, ccc);
    }

}
