package ua.foxminded.university.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.foxminded.university.model.Department;

@Repository
public class DepartmentJdbcDAO implements GenericDAO<Department> {
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(Department department) {
        jdbcTemplate.update("INSERT INTO department VALUES(?, ?, ?, ?)", department.getId(), department.getName(), department.getSpecializations(), department.getTeachers());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM department WHERE department_id=?", id);
    }

    @Override
    public Department getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM department WHERE department_id=?", new BeanPropertyRowMapper<>(Department.class), id);
    }

    @Override
    public List<Department> getAll() {
        return jdbcTemplate.query("SELECT * FROM department", new BeanPropertyRowMapper<>(Department.class));
    }

}
