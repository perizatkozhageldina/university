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
import ua.foxminded.university.model.Student;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class StudentJdbcRepositoryTest {
    private static final Student expectedStudent1 = Student.builder().id(101).academicYear(1).groupId(1).build();
    private static final Student expectedStudent2 = Student.builder().id(102).groupId(5).build();
    private static final Student expectedStudent3 = Student.builder().id(103).academicYear(2).groupId(3).build();

    @Autowired
    private StudentJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    @Sql("classpath:insertGroups.sql")
    void shouldAddStudent_whenAddMethodCalled() throws RepositoryException {
        dao.add(expectedStudent1);
        Student actualStudent = dao.getById(expectedStudent1.getId());
        assertEquals(expectedStudent1, actualStudent);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    @Sql("classpath:insertGroups.sql")
    void shouldDeleteStudent_whenDeleteMethodCalled() throws RepositoryException {
        dao.add(expectedStudent1);
        dao.deleteById(expectedStudent1.getId());
        Student actualStudent = dao.getById(expectedStudent1.getId());
        assertNull(actualStudent);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    @Sql("classpath:insertGroups.sql")
    void shouldGetStudent_whenGetByIdMethodCalled() throws RepositoryException {
        dao.add(expectedStudent1);
        Student actualStudent = dao.getById(expectedStudent1.getId());
        assertEquals(expectedStudent1, actualStudent);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    @Sql("classpath:insertGroups.sql")
    void shouldGettAllStudents_whenGetAllMethodCalled() throws RepositoryException {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(expectedStudent1);
        expectedStudents.add(expectedStudent2);
        expectedStudents.add(expectedStudent3);
        dao.add(expectedStudent1);
        dao.add(expectedStudent2);
        dao.add(expectedStudent3);
        List<Student> actualStudents = dao.getAll();
        assertEquals(expectedStudents, actualStudents);
    }
}