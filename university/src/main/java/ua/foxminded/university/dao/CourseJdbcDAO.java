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
    private static final String SELECT_ALL = "SELECT * FROM course order by id asc";
    private static final String SELECT_ONE = "SELECT * FROM course WHERE id = ?";
    private static final String INSERT = "INSERT INTO course VALUES(?, ?, ?)";
    private static final String DELETE = "DELETE FROM course WHERE id = ?";
    private static final String UPDATE = "UPDATE course set level = ?, hours = ? WHERE id = ?";

    @Autowired
    public CourseJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Course> getAll() throws DAOException {
        try {
            return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Course.class));
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't get all courses", e);
        }        
    }

    @Override
    public void add(Course course) throws DAOException {
        try {
            jdbcTemplate.update(INSERT, course.getId(), course.getLevel(),
                    course.getHours());
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't add " + course, e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't delete course with id = " + id, e);
        }

    }

    @Override
    public Course getById(long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE,
                    new BeanPropertyRowMapper<>(Course.class), id);
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't get course with id = " + id, e);
        }
    }
    
    @Override
    public void update(Course course) throws DAOException {
        try {
            jdbcTemplate.update(UPDATE, course.getLevel(),
                    course.getHours(), course.getId());
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't update " + course, e);
        }
    }
}