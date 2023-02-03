package ua.foxminded.university.dao;

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
class LectureJdbcDAOTest {
    private static final Lecture expectedLecture1 = Lecture.builder().lectureId(101).number(1).build();
    private static final Lecture expectedLecture2 = Lecture.builder().lectureId(102).number(2).build();    
    private static final Lecture expectedLecture3 = Lecture.builder().lectureId(103).number(3).build();
    
    @Autowired
    private LectureJdbcDAO dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddLecture_whenAddMethodCalled() {
        dao.add(expectedLecture1);
        Lecture actualLecture = dao.getById(expectedLecture1.getLectureId());
        assertEquals(expectedLecture1, actualLecture);        
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteLecture_whenDeleteMethodCalled() {
        dao.add(expectedLecture1);
        dao.deleteById(expectedLecture1.getLectureId());
        Lecture actualLecture = dao.getById(expectedLecture1.getLectureId());
        assertNull(actualLecture);    
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetLecture_whenGetByIdMethodCalled() {
        dao.add(expectedLecture1);
        Lecture actualLecture = dao.getById(expectedLecture1.getLectureId());
        assertEquals(expectedLecture1, actualLecture);        
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGettAllLectures_whenGetAllMethodCalled() {
        List<Lecture> expectedLectures = new ArrayList<>();
        expectedLectures.add(expectedLecture1);
        expectedLectures.add(expectedLecture2);
        expectedLectures.add(expectedLecture3);
        dao.add(expectedLecture1);
        dao.add(expectedLecture2);
        dao.add(expectedLecture3);
        List<Lecture> actualLectures = dao.getAll();
        assertEquals(expectedLectures, actualLectures);        
    }
}
