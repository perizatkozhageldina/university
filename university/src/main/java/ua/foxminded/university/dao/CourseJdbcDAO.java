package ua.foxminded.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.foxminded.university.model.Course;

@Repository
public class CourseJdbcDAO implements GenericDAO<Course> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM course";
    private static final String SELECT_ONE = "SELECT * FROM course WHERE course_id = ?";
    private static final String INSERT = "INSERT INTO course VALUES(?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM course WHERE course_id = ?";

    @Autowired
    public CourseJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public void add(Course course) {
        jdbcTemplate.update(INSERT, course.getId(), course.getName(),
                course.getDescription(), course.getCreditHours(), course.getLectures(), course.getTeacher());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Course getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_ONE,
                new BeanPropertyRowMapper<>(Course.class), id);
    }
}
