package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Lecture;

@Component
public class LectureJdbcDAO implements GenericDAO<Lecture> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM lecture";
    private static final String SELECT_ONE = "SELECT * FROM lecture WHERE id=?";
    private static final String INSERT = "INSERT INTO lecture VALUES(?, ?)";
    private static final String DELETE = "DELETE FROM lecture WHERE id=?";

    @Autowired
    public LectureJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Lecture lecture) throws DAOException {
        try {
            jdbcTemplate.update(INSERT, lecture.getId(), lecture.getNumber());
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't add " + lecture, e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't delete lecture with id " + id, e);
        }
    }

    @Override
    public Lecture getById(long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE,
                    new BeanPropertyRowMapper<>(Lecture.class), id);
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't get lecture with id " + id, e);
        }
    }

    @Override
    public List<Lecture> getAll() throws DAOException {
        try {
            return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Lecture.class));
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't get all lectures", e);
        }
    }
}
