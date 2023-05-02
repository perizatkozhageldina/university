package ua.foxminded.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
    private static final Lecture EXPECTED_LECTURE_1 = Lecture.builder().name("Lecture1").number(1).build();
    private static final Lecture EXPECTED_LECTURE_2 = Lecture.builder().name("Lecture2").number(2).build();
    private static final Lecture EXPECTED_LECTURE_3 = Lecture.builder().name("Lecture3").number(3).build();

    @Autowired
    private LectureJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddLecture_whenAddMethodCalled() {
        Lecture savedLecture = dao.save(EXPECTED_LECTURE_1);
        assertEquals(EXPECTED_LECTURE_1, savedLecture);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteLecture_whenDeleteMethodCalled() {
        Lecture savedLecture = dao.save(EXPECTED_LECTURE_1);
        dao.deleteById(EXPECTED_LECTURE_1.getId());
        boolean exists = dao.existsById(savedLecture.getId());
        assertFalse(exists);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetLecture_whenGetByIdMethodCalled() {
        dao.save(EXPECTED_LECTURE_1);
        Lecture actualLecture = dao.findById(EXPECTED_LECTURE_1.getId()).orElse(null);
        assertEquals(EXPECTED_LECTURE_1, actualLecture);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAllLectures_whenGetAllMethodCalled() {
        List<Lecture> expectedLectures = new ArrayList<>();
        expectedLectures.add(EXPECTED_LECTURE_1);
        expectedLectures.add(EXPECTED_LECTURE_2);
        expectedLectures.add(EXPECTED_LECTURE_3);
        dao.save(EXPECTED_LECTURE_1);
        dao.save(EXPECTED_LECTURE_2);
        dao.save(EXPECTED_LECTURE_3);
        List<Lecture> actualLectures = dao.findAll();
        assertEquals(expectedLectures, actualLectures);
    }
}
