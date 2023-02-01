package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Student;

@Component
public class StudentJdbcDAO implements GenericDAO<Student> {
    JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM student";
    private static final String SELECT_ONE = "SELECT * FROM student WHERE student_id=?";
    private static final String INSERT = "INSERT INTO student VALUES(?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM student WHERE student_id=?";

    @Autowired
    public StudentJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Student student) {
        jdbcTemplate.update(INSERT, student.getPersonId(), student.getName(), student.getSurname(), student.getEmail(), student.getBirthDate(), student.getGender());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Student getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_ONE,
                new BeanPropertyRowMapper<>(Student.class), id);
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Student.class));
    }
}
