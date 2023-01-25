package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.foxminded.university.model.Department;

@Repository
public class DepartmentJdbcDAO implements GenericDAO<Department> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM department";
    private static final String SELECT_ONE = "SELECT * FROM department WHERE department_id=?";
    private static final String INSERT = "INSERT INTO department VALUES(?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM department WHERE department_id=?";

    @Autowired
    public DepartmentJdbcDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Department department) {
        jdbcTemplate.update(INSERT, department.getId(), department.getName(),
                department.getSpecializations(), department.getTeachers());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Department getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_ONE,
                new BeanPropertyRowMapper<>(Department.class), id);
    }

    @Override
    public List<Department> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Department.class));
    }
}
