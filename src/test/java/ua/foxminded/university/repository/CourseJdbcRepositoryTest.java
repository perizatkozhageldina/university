package ua.foxminded.university.repository;

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
class CourseJdbcRepositoryTest {
    private static final Course EXPECTED_COURSE_1 = Course.builder().level(3).hours(36).build();
    private static final Course EXPECTED_COURSE_2 = Course.builder().level(4).hours(48).build();
    private static final Course EXPECTED_COURSE_3 = Course.builder().level(5).hours(60).build();

    @Autowired
    private CourseJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddCourse_whenAddMethodCalled() {
        Course savedCourse = dao.save(EXPECTED_COURSE_1);
        assertEquals(EXPECTED_COURSE_1, savedCourse);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteCourse_whenDeleteMethodCalled() {
        Course savedCourse = dao.save(EXPECTED_COURSE_1);
        dao.deleteById(savedCourse.getId());
        boolean exists = dao.existsById(savedCourse.getId());
        assertFalse(exists);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetCourse_whenGetByIdMethodCalled() {
        dao.save(EXPECTED_COURSE_1);
        Course actualCourse = dao.findById(EXPECTED_COURSE_1.getId()).orElse(null);
        assertEquals(EXPECTED_COURSE_1, actualCourse);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAllCourses_whenGetAllMethodCalled() {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(EXPECTED_COURSE_1);
        expectedCourses.add(EXPECTED_COURSE_2);
        expectedCourses.add(EXPECTED_COURSE_3);
        dao.save(EXPECTED_COURSE_1);
        dao.save(EXPECTED_COURSE_2);
        dao.save(EXPECTED_COURSE_3);
        List<Course> actualCourses = dao.findAll();
        assertEquals(expectedCourses, actualCourses);
    }
}