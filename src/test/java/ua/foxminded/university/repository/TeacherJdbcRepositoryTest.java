package ua.foxminded.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final Teacher expectedTeacher1 = Teacher.builder().category("category1").hours(36).build();
    private static final Teacher expectedTeacher2 = Teacher.builder().category("category2").hours(48).build();
    private static final Teacher expectedTeacher3 = Teacher.builder().category("category3").hours(60).build();

    @Autowired
    private TeacherJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddTeacher_whenAddMethodCalled() {
        Teacher savedTeacher = dao.save(expectedTeacher1);
        assertEquals(expectedTeacher1, savedTeacher);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteTeacher_whenDeleteMethodCalled() {
        Teacher savedTeacher = dao.save(expectedTeacher1);
        dao.deleteById(expectedTeacher1.getId());
        boolean exists = dao.existsById(savedTeacher.getId());
        assertFalse(exists);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetTeacher_whenGetByIdMethodCalled() {
        dao.save(expectedTeacher1);
        Teacher actualTeacher = dao.findById(expectedTeacher1.getId()).orElse(null);
        assertEquals(expectedTeacher1, actualTeacher);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAllTeachers_whenGetAllMethodCalled() {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(expectedTeacher1);
        expectedTeachers.add(expectedTeacher2);
        expectedTeachers.add(expectedTeacher3);
        dao.save(expectedTeacher1);
        dao.save(expectedTeacher2);
        dao.save(expectedTeacher3);
        List<Teacher> actualTeachers = dao.findAll();
        assertEquals(expectedTeachers, actualTeachers);
    }
}
