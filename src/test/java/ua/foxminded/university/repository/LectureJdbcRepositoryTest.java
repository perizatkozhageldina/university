package ua.foxminded.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.model.Lecture;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class LectureJdbcRepositoryTest {
    private static final Lecture expectedLecture1 = Lecture.builder().id(101).number(1).build();
    private static final Lecture expectedLecture2 = Lecture.builder().id(102).number(2).build();
    private static final Lecture expectedLecture3 = Lecture.builder().id(103).number(3).build();

    @Autowired
    private LectureJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddLecture_whenAddMethodCalled() {
        dao.save(expectedLecture1);
        Optional<Lecture> actualLecture = dao.findById(expectedLecture1.getId());
        assertEquals(expectedLecture1, actualLecture);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteLecture_whenDeleteMethodCalled() {
        dao.save(expectedLecture1);
        dao.deleteById(expectedLecture1.getId());
        Optional<Lecture> actualLecture = dao.findById(expectedLecture1.getId());
        assertNull(actualLecture);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetLecture_whenGetByIdMethodCalled() {
        dao.save(expectedLecture1);
        Optional<Lecture> actualLecture = dao.findById(expectedLecture1.getId());
        assertEquals(expectedLecture1, actualLecture);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGettAllLectures_whenGetAllMethodCalled() {
        List<Lecture> expectedLectures = new ArrayList<>();
        expectedLectures.add(expectedLecture1);
        expectedLectures.add(expectedLecture2);
        expectedLectures.add(expectedLecture3);
        dao.save(expectedLecture1);
        dao.save(expectedLecture2);
        dao.save(expectedLecture3);
        List<Lecture> actualLectures = dao.findAll();
        assertEquals(expectedLectures, actualLectures);
    }
}
