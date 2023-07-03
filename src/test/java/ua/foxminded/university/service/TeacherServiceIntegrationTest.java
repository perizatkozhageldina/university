package ua.foxminded.university.service;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.repository.TeacherJdbcRepository;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TeacherServiceIntegrationTest {
    @Autowired
    private TeacherService service;

    @Autowired
    private TeacherJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddAndGetTeacher_whenServiceAddAndGetMethodsCalled() {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name("Name").surname("Surname").hours(12).build();
        TeacherDTO createdTeacher = service.save(teacher);
        TeacherDTO retrievedTeacher = service.getById(createdTeacher.getId());
        Assert.assertNotNull(retrievedTeacher);
        Assert.assertEquals(teacher.getName(), retrievedTeacher.getName());
        Assert.assertEquals(teacher.getSurname(), retrievedTeacher.getSurname());
        Assert.assertEquals(teacher.getHours(), retrievedTeacher.getHours());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldGetAllTeachers_whenGetAllMethodCalled() {
        TeacherDTO teacher1 = TeacherDTO.builder().id(1L).name("Lecture1").surname("Surname").hours(12).build();
        TeacherDTO teacher2 = TeacherDTO.builder().id(2L).name("Lecture2").surname("Surname").hours(12).build();
        service.save(teacher1);
        service.save(teacher2);

        List<TeacherDTO> allTeachers = service.getAll();
        Assert.assertEquals(2, allTeachers.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteTeacher_whenDeleteMethodCalled() {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name("Lecture1").surname("Surname").hours(12).build();
        TeacherDTO savedTeacher = service.save(teacher);
        service.deleteById(savedTeacher.getId());
        Assert.assertFalse(dao.existsById(savedTeacher.getId()));
    }
}