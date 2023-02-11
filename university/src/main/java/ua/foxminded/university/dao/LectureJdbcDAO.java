package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Lecture;

@Component
public class LectureJdbcDAO implements GenericDAO<Lecture> {
    JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM lecture";
    private static final String SELECT_ONE = "SELECT * FROM lecture WHERE lectureId=?";
    private static final String INSERT = "INSERT INTO lecture VALUES(?, ?)";
    private static final String DELETE = "DELETE FROM lecture WHERE lectureId=?";

    @Autowired
    public LectureJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Lecture lecture) {
        jdbcTemplate.update(INSERT, lecture.getLectureId(), lecture.getNumber());
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Lecture getById(long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE,
                    new BeanPropertyRowMapper<>(Lecture.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Lecture> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Lecture.class));
    }
}
