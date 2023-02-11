package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Course;

@Component
public class CourseJdbcDAO implements GenericDAO<Course> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM course";
    private static final String SELECT_ONE = "SELECT * FROM course WHERE courseId = ?";
    private static final String INSERT = "INSERT INTO course VALUES(?, ?, ?)";
    private static final String DELETE = "DELETE FROM course WHERE courseId = ?";

    @Autowired
    public CourseJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Course> getAll() {
        try {
            return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Course.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void add(Course course) {
        jdbcTemplate.update(INSERT, course.getCourseId(), course.getLevel(),
                course.getHours());
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Course getById(long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE,
                    new BeanPropertyRowMapper<>(Course.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
