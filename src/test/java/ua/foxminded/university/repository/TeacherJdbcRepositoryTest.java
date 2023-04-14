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
    private static final Teacher expectedTeacher1 = Teacher.builder().id(101).hours(36).build();
    private static final Teacher expectedTeacher2 = Teacher.builder().id(102).hours(48).build();
    private static final Teacher expectedTeacher3 = Teacher.builder().id(103).hours(60).build();

    @Autowired
    private TeacherJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddTeacher_whenAddMethodCalled() throws RepositoryException {
        dao.add(expectedTeacher1);
        Teacher actualTeacher = dao.getById(expectedTeacher1.getId());
        assertEquals(expectedTeacher1, actualTeacher);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteTeacher_whenDeleteMethodCalled() throws RepositoryException {
        dao.add(expectedTeacher1);
        dao.deleteById(expectedTeacher1.getId());
        Teacher actualTeacher = dao.getById(expectedTeacher1.getId());
        assertNull(actualTeacher);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetTeacher_whenGetByIdMethodCalled() throws RepositoryException {
        dao.add(expectedTeacher1);
        Teacher actualTeacher = dao.getById(expectedTeacher1.getId());
        assertEquals(expectedTeacher1, actualTeacher);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGettAllTeachers_whenGetAllMethodCalled() throws RepositoryException {
        List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(expectedTeacher1);
        expectedTeachers.add(expectedTeacher2);
        expectedTeachers.add(expectedTeacher3);
        dao.add(expectedTeacher1);
        dao.add(expectedTeacher2);
        dao.add(expectedTeacher3);
        List<Teacher> actualTeachers = dao.getAll();
        assertEquals(expectedTeachers, actualTeachers);
    }
}
