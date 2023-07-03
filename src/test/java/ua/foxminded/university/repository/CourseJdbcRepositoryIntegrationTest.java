package ua.foxminded.university.repository;

import org.junit.jupiter.api.Test;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.university.model.Course;

import java.util.List;

@DataJpaTest
public class CourseJdbcRepositoryIntegrationTest {
    @Autowired
    private CourseJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldSaveCourse_whenSaveMethodCalled() {
        Course course = Course.builder().id(1L).name("Course").level(1).hours(12).build();
        Course savedCourse = dao.save(course);
        Assert.assertEquals(savedCourse.getName(), "Course");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindById_whenFindByIdMethodCalled() {
        Course course = Course.builder().id(1L).name("Course").level(1).hours(12).build();
        dao.save(course);
        Course foundCourse = dao.findById(course.getId()).orElse(null);
        Assert.assertNotNull(foundCourse);
        Assert.assertEquals(foundCourse.getName(),"Course");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindAllCourses_whenFindAllMethodCalled() {
        Course course1 = Course.builder().id(1L).name("Course1").level(1).hours(12).build();
        Course course2 = Course.builder().id(2L).name("Course2").level(2).hours(24).build();
        dao.save(course1);
        dao.save(course2);
        List<Course> courses = dao.findAll();
        Assert.assertEquals(2, courses.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void testDeleteCourse() {
        Course course = Course.builder().id(1L).name("Course").level(1).hours(12).build();
        Course savedCourse = dao.save(course);
        dao.deleteById(savedCourse.getId());
        Assert.assertNull(dao.findById(savedCourse.getId()).orElse(null));
    }
}
//