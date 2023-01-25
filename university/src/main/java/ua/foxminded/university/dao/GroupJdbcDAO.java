package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.foxminded.university.model.Group;

public class GroupJdbcDAO implements GenericDAO<Group> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM group";
    private static final String SELECT_ONE = "SELECT * FROM group WHERE group_id=?";
    private static final String INSERT = "INSERT INTO group VALUES(?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM group WHERE group_id=?";

    @Autowired
    public GroupJdbcDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Group group) {
        jdbcTemplate.update(INSERT, group.getId(), group.getName(), group.getCourses(), group.getStudents());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Group getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_ONE,
                new BeanPropertyRowMapper<>(Group.class), id);
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Group.class));
    }
}
