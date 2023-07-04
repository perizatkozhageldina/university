package ua.foxminded.university.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.foxminded.university.dto.StudentDTO;
import ua.foxminded.university.repository.StudentJdbcRepository;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StudentServiceIntegrationTest {
    @Autowired
    private StudentService service;

    @Autowired
    private StudentJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddAndGetStudent_whenServiceAddAndGetMethodsCalled() {
        StudentDTO student = StudentDTO.builder().id(1L).name("Name").surname("Surname").academicYear(1).build();
        StudentDTO createdStudent = service.save(student);
        Assertions.assertNotNull(createdStudent.getId());

        StudentDTO retrievedStudent = service.getById(createdStudent.getId());
        Assertions.assertNotNull(retrievedStudent);
        Assertions.assertEquals(student.getName(), retrievedStudent.getName());
        Assertions.assertEquals(student.getSurname(), retrievedStudent.getSurname());
        Assertions.assertEquals(student.getAcademicYear(), retrievedStudent.getAcademicYear());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldGetAllStudents_whenGetAllMethodCalled() {
        StudentDTO student1 = StudentDTO.builder().id(1L).name("Name").surname("Surname").academicYear(1).build();
        StudentDTO student2 = StudentDTO.builder().id(2L).name("Name").surname("Surname").academicYear(2).build();
        service.save(student1);
        service.save(student2);

        List<StudentDTO> allStudents = service.getAll();
        Assertions.assertEquals(2, allStudents.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteStudent_whenDeleteMethodCalled() {
        StudentDTO student = StudentDTO.builder().id(1L).name("Name").surname("Surname").academicYear(1).build();
        StudentDTO savedStudent = service.save(student);
        service.deleteById(savedStudent.getId());
        Assertions.assertFalse(dao.existsById(savedStudent.getId()));
    }
}