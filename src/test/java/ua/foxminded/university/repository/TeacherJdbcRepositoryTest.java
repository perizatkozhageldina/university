package ua.foxminded.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.model.Teacher;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class TeacherJdbcRepositoryTest {
    private static final Teacher EXPECTED_TEACHER_1 = Teacher.builder().category("category1").hours(36).build();
    private static final Teacher EXPECTED_TEACHER_2 = Teacher.builder().category("category2").hours(48).build();
    private static final Teacher EXPECTED_TEACHER_3 = Teacher.builder().category("category3").hours(60).build();

    @Autowired
    private TeacherJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddTeacher_whenAddMethodCalled() {
        Teacher savedTeacher = dao.save(EXPECTED_TEACHER_1);
        assertEquals(EXPECTED_TEACHER_1, savedTeacher);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteTeacher_whenDeleteMethodCalled() {
        Teacher savedTeacher = dao.save(EXPECTED_TEACHER_1);
        dao.deleteById(EXPECTED_TEACHER_1.getId());
        boolean exists = dao.existsById(savedTeacher.getId());
        assertFalse(exists);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetTeacher_whenGetByIdMethodCalled() {
        dao.save(EXPECTED_TEACHER_1);
        Teacher actualTeacher = dao.findById(EXPECTED_TEACHER_1.getId()).orElse(null);
        assertEquals(EXPECTED_TEACHER_1, actualTeacher);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAllTeachers_whenGetAllMethodCalled() {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(EXPECTED_TEACHER_1);
        expectedTeachers.add(EXPECTED_TEACHER_2);
        expectedTeachers.add(EXPECTED_TEACHER_3);
        dao.save(EXPECTED_TEACHER_1);
        dao.save(EXPECTED_TEACHER_2);
        dao.save(EXPECTED_TEACHER_3);
        List<Teacher> actualTeachers = dao.findAll();
        assertEquals(expectedTeachers, actualTeachers);
    }
}
//