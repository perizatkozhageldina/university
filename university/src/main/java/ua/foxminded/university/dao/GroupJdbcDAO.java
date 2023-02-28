package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Group;

@Component
public class GroupJdbcDAO implements GenericDAO<Group> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM groups";
    private static final String SELECT_ONE = "SELECT * FROM groups WHERE id=?";
    private static final String INSERT = "INSERT INTO groups VALUES(?, ?)";
    private static final String DELETE = "DELETE FROM groups WHERE id=?";
    private static final String UPDATE = "UPDATE groups set name = ? WHERE id = ?";

    @Autowired
    public GroupJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Group group) throws DAOException {
        try {
            jdbcTemplate.update(INSERT, group.getId(), group.getName());
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't add " + group, e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't delete group  with id " + id, e);
        }
    }

    @Override
    public Group getById(long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE,
                    new BeanPropertyRowMapper<>(Group.class), id);
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't get group with id " + id, e);
        }
    }

    @Override
    public List<Group> getAll() throws DAOException {
        try {
            return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Group.class));
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't get all groups", e);
        }
    }

    @Override
    public void update(Group group) throws DAOException {
        try {
            jdbcTemplate.update(UPDATE, group.getName(), group.getId());
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't update " + group, e);
        }
    }
}
