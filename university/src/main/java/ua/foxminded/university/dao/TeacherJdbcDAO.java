package ua.foxminded.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.foxminded.university.model.Teacher;

public class TeacherJdbcDAO implements GenericDAO<Teacher> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM lecture";
    private static final String SELECT_ONE = "SELECT * FROM lecture WHERE lecture_id=?";
    private static final String INSERT = "INSERT INTO lecture VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM lecture WHERE lecture_id=?";

    @Autowired
    public TeacherJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Teacher teacher) {
        jdbcTemplate.update(INSERT, teacher.getId(), teacher.getName(), teacher.getSurname(), teacher.getEmail(),
                teacher.getBirthDate(), teacher.getGender(), teacher.getSalary());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Teacher getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_ONE,
                new BeanPropertyRowMapper<>(Teacher.class), id);
    }

    @Override
    public List<Teacher> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Teacher.class));
    }
}