package ua.foxminded.university.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.university.model.Teacher;

import java.util.List;

@DataJpaTest
public class TeacherJdbcRepositoryIntegrationTest {

    @Autowired
    private TeacherJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldSaveTeacher_whenSaveMethodCalled() {
        Teacher teacher = Teacher.builder().id(1L).name("Name").surname("Surname").hours(10).category("middle").build();
        Teacher savedTeacher = dao.save(teacher);
        assertEquals(savedTeacher.getName(), "Name");
        assertEquals(savedTeacher.getSurname(), "Surname");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindById_whenFindByIdMethodCalled() {
        Teacher teacher = Teacher.builder().id(1L).name("Name").surname("Surname").hours(10).category("middle").build();
        dao.save(teacher);
        Teacher foundTeacher = dao.findById(teacher.getId()).orElse(null);
        assertNotNull(foundTeacher);
        assertEquals(foundTeacher.getName(),"Name");
        assertEquals(foundTeacher.getSurname(), "Surname");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindAllTeachers_whenFindAllMethodCalled() {
        Teacher teacher1 = Teacher.builder().id(1L).name("Name").surname("Surname").hours(10).category("middle").build();
        Teacher teacher2 = Teacher.builder().id(2L).name("Name").surname("Surname").hours(10).category("middle").build();
        dao.save(teacher1);
        dao.save(teacher2);
        List<Teacher> teachers = dao.findAll();
        assertEquals(2, teachers.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void testDeleteTeacher() {
        Teacher teacher = Teacher.builder().id(1L).name("Name").surname("Surname").hours(10).category("middle").build();
        Teacher savedTeacher = dao.save(teacher);
        dao.deleteById(savedTeacher.getId());
        assertNull(dao.findById(savedTeacher.getId()).orElse(null));
    }
}