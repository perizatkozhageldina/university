package ua.foxminded.university.dao;

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
class StudentJdbcDAOTest {
    private static final Student expectedStudent1 = Student.builder().studentId(101).academicYear(1).build();
    private static final Student expectedStudent2 = Student.builder().studentId(102).academicYear(3).build();    
    private static final Student expectedStudent3 = Student.builder().studentId(103).academicYear(2).build();
    
    @Autowired
    private StudentJdbcDAO dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddStudent_whenAddMethodCalled() {
        dao.add(expectedStudent1);
        Student actualStudent = dao.getById(expectedStudent1.getStudentId());
        assertEquals(expectedStudent1, actualStudent);        
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteStudent_whenDeleteMethodCalled() {
        dao.add(expectedStudent1);
        dao.deleteById(expectedStudent1.getStudentId());
        Student actualStudent = dao.getById(expectedStudent1.getStudentId());
        assertNull(actualStudent);    
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetStudent_whenGetByIdMethodCalled() {
        dao.add(expectedStudent1);
        Student actualStudent = dao.getById(expectedStudent1.getStudentId());
        assertEquals(expectedStudent1, actualStudent);        
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGettAllStudents_whenGetAllMethodCalled() {
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
