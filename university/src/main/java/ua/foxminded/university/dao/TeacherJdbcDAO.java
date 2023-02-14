package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Teacher;

@Component
public class TeacherJdbcDAO implements GenericDAO<Teacher> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM teacher";
    private static final String SELECT_ONE = "SELECT * FROM teacher WHERE teacherId=?";
    private static final String INSERT = "INSERT INTO teacher VALUES(?, ?, ?)";
    private static final String DELETE = "DELETE FROM teacher WHERE teacherId=?";

    @Autowired
    public TeacherJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Teacher teacher) throws DAOException {
        try {
            jdbcTemplate.update(INSERT, teacher.getTeacherId(), teacher.getCategory(), teacher.getHours());
        } catch (DataAccessException e) {
            String msg = "Couldn't add " + teacher;
            throw new DAOException(msg, e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            String msg = "Couldn't delete teacher with id " + id;
            throw new DAOException(msg, e);
        }
    }

    @Override
    public Teacher getById(long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE,
                    new BeanPropertyRowMapper<>(Teacher.class), id);
        } catch (DataAccessException e) {
            String msg = "Couldn't get teacher with id " + id;
            throw new DAOException(msg, e);
        }
    }

    @Override
    public List<Teacher> getAll() throws DAOException {
        try {
            return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (DataAccessException e) {
            String msg = "Couldn't get all teachers";
            throw new DAOException(msg, e);
        }
    }
}