package ua.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.university.model.Room;

@Component
public class RoomJdbcDAO implements GenericDAO<Room> {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM room";
    private static final String SELECT_ONE = "SELECT * FROM room WHERE roomId = ?";
    private static final String INSERT = "INSERT INTO room VALUES(?, ?)";
    private static final String DELETE = "DELETE FROM room WHERE roomId = ?";

    @Autowired
    public RoomJdbcDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Room> getAll() throws DAOException {
        try {
            return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Room.class));
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't get all rooms", e);
        }
    }

    @Override
    public void add(Room room) throws DAOException {
        try {
            jdbcTemplate.update(INSERT, room.getRoomId(), room.getCapacity());
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't add " + room, e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't delete room with id " + id, e);
        }
    }

    @Override
    public Room getById(long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE, new BeanPropertyRowMapper<>(Room.class), id);
        } catch (DataAccessException e) {
            throw new DAOException("Couldn't get room with id " + id, e);
        }
    }
}
