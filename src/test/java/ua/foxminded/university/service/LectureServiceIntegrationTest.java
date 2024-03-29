package ua.foxminded.university.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.foxminded.university.dto.LectureDTO;
import ua.foxminded.university.repository.LectureJdbcRepository;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LectureServiceIntegrationTest {

    @Autowired
    private LectureService service;

    @Autowired
    private LectureJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddAndGetLecture_whenServiceAddAndGetMethodsCalled() {
        LectureDTO lecture = LectureDTO.builder().id(1L).name("Lecture").number(10).build();
        LectureDTO createdLecture = service.save(lecture);
        LectureDTO retrievedLecture = service.getById(createdLecture.getId());
        assertNotNull(retrievedLecture);
        assertEquals(lecture.getName(), retrievedLecture.getName());
        assertEquals(lecture.getNumber(), retrievedLecture.getNumber());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldGetAllLectures_whenGetAllMethodCalled() {
        LectureDTO lecture1 = LectureDTO.builder().id(1L).name("Lecture").number(10).build();
        LectureDTO lecture2 = LectureDTO.builder().id(2L).name("Lecture").number(10).build();
        service.save(lecture1);
        service.save(lecture2);

        List<LectureDTO> allLectures = service.getAll();
        assertEquals(2, allLectures.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteLecture_whenDeleteMethodCalled() {
        LectureDTO lecture = LectureDTO.builder().id(1L).name("Lecture").number(10).build();
        LectureDTO savedLecture = service.save(lecture);
        service.deleteById(savedLecture.getId());
        assertFalse(dao.existsById(savedLecture.getId()));
    }
}