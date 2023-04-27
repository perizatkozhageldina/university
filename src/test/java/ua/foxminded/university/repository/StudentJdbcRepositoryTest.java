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
    void shouldAddStudent_whenAddMethodCalled() {
        dao.save(expectedStudent1);
        Optional<Student> actualStudent = dao.findById(expectedStudent1.getId());
        assertEquals(expectedStudent1, actualStudent);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    @Sql("classpath:insertGroups.sql")
    void shouldDeleteStudent_whenDeleteMethodCalled() {
        dao.save(expectedStudent1);
        dao.deleteById(expectedStudent1.getId());
        Optional<Student> actualStudent = dao.findById(expectedStudent1.getId());
        assertNull(actualStudent);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    @Sql("classpath:insertGroups.sql")
    void shouldGetStudent_whenGetByIdMethodCalled() {
        dao.save(expectedStudent1);
        Optional<Student> actualStudent = dao.findById(expectedStudent1.getId());
        assertEquals(expectedStudent1, actualStudent);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    @Sql("classpath:insertGroups.sql")
    void shouldGettAllStudents_whenGetAllMethodCalled() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(expectedStudent1);
        expectedStudents.add(expectedStudent2);
        expectedStudents.add(expectedStudent3);
        dao.save(expectedStudent1);
        dao.save(expectedStudent2);
        dao.save(expectedStudent3);
        List<Student> actualStudents = dao.findAll();
        assertEquals(expectedStudents, actualStudents);
    }
}
