package ua.foxminded.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.university.model.Student;

import java.util.List;

@DataJpaTest
public class StudentJdbcRepositoryIntegrationTest {

    @Autowired
    private StudentJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldSaveStudent_whenSaveMethodCalled() {
        Student student = Student.builder().id(1L).name("Name").surname("Surname").academicYear(1).build();
        Student savedStudent = dao.save(student);
        assertNotNull(savedStudent.getId());
        assertEquals(savedStudent.getName(), "Name");
        assertEquals(savedStudent.getSurname(), "Surname");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindById_whenFindByIdMethodCalled() {
        Student student = Student.builder().id(1L).name("Name").surname("Surname").academicYear(1).build();
        dao.save(student);
        Student foundStudent = dao.findById(student.getId()).orElse(null);
        assertNotNull(foundStudent);
        assertEquals(foundStudent.getName(),"Name");
        assertEquals(foundStudent.getSurname(), "Surname");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindAllStudents_whenFindAllMethodCalled() {
        Student student1 = Student.builder().id(1L).name("Name").surname("Surname").academicYear(1).build();
        Student student2 = Student.builder().id(2L).name("Name").surname("Surname").academicYear(1).build();
        dao.save(student1);
        dao.save(student2);
        List<Student> students = dao.findAll();
        assertEquals(2, students.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void testDeleteStudent() {
        Student student = Student.builder().id(1L).name("Name").surname("Surname").academicYear(1).build();
        Student savedStudent = dao.save(student);
        dao.deleteById(savedStudent.getId());
        assertNull(dao.findById(savedStudent.getId()).orElse(null));
    }
}