package ua.foxminded.university.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.repository.CourseJdbcRepository;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CourseServiceIntegrationTest {

    @Autowired
    private CourseService service;

    @Autowired
    private CourseJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddAndGetCourse_whenServiceAddAndGetMethodsCalled() {
        CourseDTO course = CourseDTO.builder().id(1L).name("Course").hours(12).level(1).build();
        CourseDTO createdCourse = service.save(course);
        CourseDTO retrievedCourse = service.getById(createdCourse.getId());
        Assertions.assertNotNull(retrievedCourse);
        Assertions.assertEquals(course.getName(), retrievedCourse.getName());
        Assertions.assertEquals(course.getHours(), retrievedCourse.getHours());
        Assertions.assertEquals(course.getLevel(), retrievedCourse.getLevel());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldGetAllCourses_whenGetAllMethodCalled() {
        CourseDTO course1 = CourseDTO.builder().id(1).name("Course").level(1).hours(12).build();
        CourseDTO course2 = CourseDTO.builder().id(2).name("Course").level(1).hours(12).build();
        service.save(course1);
        service.save(course2);

        List<CourseDTO> allStudents = service.getAll();
        Assertions.assertEquals(2, allStudents.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteCourse_whenDeleteMethodCalled() {
        CourseDTO course = CourseDTO.builder().id(1L).name("Course").hours(12).level(1).build();
        CourseDTO savedCourse = service.save(course);
        service.deleteById(savedCourse.getId());
        Assertions.assertFalse(dao.existsById(savedCourse.getId()));
    }
}