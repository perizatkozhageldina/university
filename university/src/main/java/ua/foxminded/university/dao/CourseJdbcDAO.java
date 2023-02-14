package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public List<Course> getAll() throws DAOException {
        try {
            return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Course.class));
        } catch (DataAccessException e) {
            String msg = "Couldn't get all courses";
            throw new DAOException(msg, e);
        }        
    }

    @Override
    public void add(Course course) throws DAOException {
        try {
            jdbcTemplate.update(INSERT, course.getCourseId(), course.getLevel(),
                    course.getHours());
        } catch (DataAccessException e) {
            String msg = "Couldn't add " + course;
            throw new DAOException(msg, e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            String msg = "Couldn't delete course with id = " + id;
            throw new DAOException(msg, e);
        }

    }

    @Override
    public Course getById(long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE,
                    new BeanPropertyRowMapper<>(Course.class), id);
        } catch (DataAccessException e) {
            String msg = "Couldn't get course with id = " + id;
            throw new DAOException(msg, e);
        }
    }
}
