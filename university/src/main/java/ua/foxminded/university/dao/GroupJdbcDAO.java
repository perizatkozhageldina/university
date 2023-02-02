package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Group;

@Component
public class GroupJdbcDAO implements GenericDAO<Group> {
    JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM groups";
    private static final String SELECT_ONE = "SELECT * FROM groups WHERE groupId=?";
    private static final String INSERT = "INSERT INTO groups VALUES(?, ?)";
    private static final String DELETE = "DELETE FROM groups WHERE groupId=?";

    @Autowired
    public GroupJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Group group) {
        jdbcTemplate.update(INSERT, group.getGroupId(), group.getStudentCount());
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Group getById(long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE,
                    new BeanPropertyRowMapper<>(Group.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Group> getAll() {
        try {
            return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Group.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
