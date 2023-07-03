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
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Student;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class StudentJdbcRepositoryTest {
    private static final Student EXPECTED_STUDENT_1 = Student.builder().academicYear(1).name("Student1").build();
    private static final Student EXPECTED_STUDENT_2 = Student.builder().academicYear(2).name("Student2").build();
    private static final Student EXPECTED_STUDENT_3 = Student.builder().academicYear(3).name("Student3").build();
    private static final Group GROUP = Group.builder().name("Group1").build();

    @Autowired
    private StudentJdbcRepository dao;
    @Autowired
    private GroupJdbcRepository groupDao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddStudent_whenAddMethodCalled() {
        Group savedGroup = groupDao.save(GROUP);
        EXPECTED_STUDENT_1.setGroupId(savedGroup.getId());
        Student savedStudent = dao.save(EXPECTED_STUDENT_1);
        assertEquals(EXPECTED_STUDENT_1, savedStudent);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteStudent_whenDeleteMethodCalled() {
        Group savedGroup = groupDao.save(GROUP);
        EXPECTED_STUDENT_1.setGroupId(savedGroup.getId());
        Student savedStudent = dao.save(EXPECTED_STUDENT_1);
        dao.deleteById(EXPECTED_STUDENT_1.getId());
        boolean exists = dao.existsById(savedStudent.getId());
        assertFalse(exists);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetStudent_whenGetByIdMethodCalled() {
        Group savedGroup = groupDao.save(GROUP);
        EXPECTED_STUDENT_1.setGroupId(savedGroup.getId());
        dao.save(EXPECTED_STUDENT_1);
        Student actualStudent = dao.findById(EXPECTED_STUDENT_1.getId()).orElse(null);
        assertEquals(EXPECTED_STUDENT_1, actualStudent);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAllStudents_whenGetAllMethodCalled() {
        Group savedGroup = groupDao.save(GROUP);
        EXPECTED_STUDENT_1.setGroupId(savedGroup.getId());
        EXPECTED_STUDENT_2.setGroupId(savedGroup.getId());
        EXPECTED_STUDENT_3.setGroupId(savedGroup.getId());
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(EXPECTED_STUDENT_1);
        expectedStudents.add(EXPECTED_STUDENT_2);
        expectedStudents.add(EXPECTED_STUDENT_3);
        dao.save(EXPECTED_STUDENT_1);
        dao.save(EXPECTED_STUDENT_2);
        dao.save(EXPECTED_STUDENT_3);
        List<Student> actualStudents = dao.findAll();
        assertEquals(expectedStudents, actualStudents);
    }
}
//