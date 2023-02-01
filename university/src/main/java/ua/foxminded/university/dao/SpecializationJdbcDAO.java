package ua.foxminded.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Specialization;

@Component
public class SpecializationJdbcDAO implements GenericDAO<Specialization> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM specialization";
    private static final String SELECT_ONE = "SELECT * FROM specialization WHERE specialization_id=?";
    private static final String INSERT = "INSERT INTO specialization VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM specialization WHERE specialization_id=?";

    @Autowired
    public SpecializationJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Specialization specialization) {
        jdbcTemplate.update(INSERT, specialization.getId(), specialization.getName(), specialization.getGroups());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Specialization getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_ONE,
                new BeanPropertyRowMapper<>(Specialization.class), id);
    }

    @Override
    public List<Specialization> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Specialization.class));
    }
}
