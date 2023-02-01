package ua.foxminded.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Lecture;

@Component
public class LectureJdbcDAO implements GenericDAO<Lecture> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM lecture";
    private static final String SELECT_ONE = "SELECT * FROM lecture WHERE lecture_id=?";
    private static final String INSERT = "INSERT INTO lecture VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM lecture WHERE lecture_id=?";

    @Autowired
    public LectureJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Lecture lecture) {
        jdbcTemplate.update(INSERT, lecture.getId(), lecture.getTheme(), lecture.getStartTime(), lecture.getEndTime(), lecture.getAudience());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Lecture getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_ONE,
                new BeanPropertyRowMapper<>(Lecture.class), id);
    }

    @Override
    public List<Lecture> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Lecture.class));
    }
}
