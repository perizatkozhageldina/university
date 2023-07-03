package ua.foxminded.university.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.university.model.Lecture;

import java.util.List;

@DataJpaTest
public class LectureJdbcRepositoryIntegrationTest {
    @Autowired
    private LectureJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldSaveLecture_whenSaveMethodCalled() {
        Lecture lecture = Lecture.builder().id(1L).name("Lecture").number(1).build();
        Lecture savedLecture = dao.save(lecture);
        Assert.assertEquals(savedLecture.getName(), "Lecture");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindById_whenFindByIdMethodCalled() {
        Lecture lecture = Lecture.builder().id(1L).name("Lecture").number(1).build();
        dao.save(lecture);
        Lecture foundLecture = dao.findById(lecture.getId()).orElse(null);
        Assert.assertNotNull(foundLecture);
        Assert.assertEquals(foundLecture.getName(),"Lecture");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindAllLectures_whenFindAllMethodCalled() {
        Lecture lecture1 = Lecture.builder().id(1L).name("Lecture1").number(1).build();
        Lecture lecture2 = Lecture.builder().id(2L).name("Lecture2").number(2).build();
        dao.save(lecture1);
        dao.save(lecture2);
        List<Lecture> lectures = dao.findAll();
        Assert.assertEquals(2, lectures.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void testDeleteLecture() {
        Lecture lecture = Lecture.builder().id(1L).name("Lecture").number(1).build();
        Lecture savedLecture = dao.save(lecture);
        dao.deleteById(savedLecture.getId());
        Assert.assertNull(dao.findById(savedLecture.getId()).orElse(null));
    }
}
