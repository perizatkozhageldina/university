package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Student;

@Component
public class StudentJdbcDAO implements GenericDAO<Student> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM student order by id asc";
    private static final String SELECT_ONE = "SELECT * FROM student WHERE id=?";
    private static final String INSERT = "INSERT INTO student VALUES(?, ?, ?)";
    private static final String DELETE = "DELETE FROM student WHERE id=?";
    private static final String UPDATE = "UPDATE student set academicYear = ?, groupId = ? WHERE id = ?";

    @Autowired
    public StudentJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Student student) throws DAOException {
        try {
        jdbcTemplate.update(INSERT, student.getId(), student.getAcademicYear(), student.getGroupId());
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't add " + student, e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {
        try {
        jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't delete student with id " + id, e);
        }
    }

    @Override
    public Student getById(long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE,
                    new BeanPropertyRowMapper<>(Student.class), id);
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't get student with id " + id, e);
        }
    }

    @Override
    public List<Student> getAll() throws DAOException {
        try {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Student.class));
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't get all students", e);
        }
    }

    @Override
    public void update(Student student) throws DAOException {
        try {
            jdbcTemplate.update(UPDATE, student.getAcademicYear(), student.getGroupId(), student.getId());
            } catch (DataAccessException e) {
                throw new DAOException("Couldn't update " + student, e);
            }
    }
}