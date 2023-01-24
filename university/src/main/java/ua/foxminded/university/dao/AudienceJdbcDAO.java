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
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public AudienceJdbcDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public List<Audience> getAll() {
        return jdbcTemplate.query("SELECT * FROM audience", new BeanPropertyRowMapper<>(Audience.class));
    }
    
    @Override
    public void add(Audience audience) {
        jdbcTemplate.update("INSERT INTO audience VALUES(?, ?, ?)", audience.getId(), audience.getRoom(), audience.getCapacity());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM audience WHERE audience_id = ?", id);
    }

    @Override
    public Audience getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM audience WHERE audience_id = ?", new BeanPropertyRowMapper<>(Audience.class), id);
    }
}
