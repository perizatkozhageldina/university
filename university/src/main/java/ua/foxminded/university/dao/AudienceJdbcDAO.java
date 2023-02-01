package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Audience;

@Component
public class AudienceJdbcDAO implements GenericDAO<Audience> {
    JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM audience";
    private static final String SELECT_ONE = "SELECT * FROM audience WHERE audience_id = ?";
    private static final String INSERT = "INSERT INTO audience VALUES(?, ?, ?)";
    private static final String DELETE = "DELETE FROM audience WHERE audience_id = ?";
    
    @Autowired
    public AudienceJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public List<Audience> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Audience.class));
    }
    
    @Override
    public void add(Audience audience) {
        jdbcTemplate.update(INSERT, audience.getAudienceId(), audience.getRoom(), audience.getCapacity());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Audience getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_ONE, new BeanPropertyRowMapper<>(Audience.class), id);
    }
}
