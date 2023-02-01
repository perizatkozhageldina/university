package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.School;

@Component
public class SchoolJdbcDAO implements GenericDAO<School> {
    JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM school";
    private static final String SELECT_ONE = "SELECT * FROM school WHERE school_id=?";
    private static final String INSERT = "INSERT INTO school VALUES(?, ?, ?)";
    private static final String DELETE = "DELETE FROM school WHERE school_id=?";

    @Autowired
    public SchoolJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(School school) {
        jdbcTemplate.update(INSERT, school.getSchoolId(), school.getName(), school.getDepartments());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public School getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_ONE,
                new BeanPropertyRowMapper<>(School.class), id);
    }

    @Override
    public List<School> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(School.class));
    }
}
