package ua.foxminded.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.DAOException;
import ua.foxminded.university.dao.RoomJdbcDAO;
import ua.foxminded.university.model.Room;

@Service
public class RoomService {
    private RoomJdbcDAO dao;
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    public RoomService(RoomJdbcDAO dao) {
        this.dao = dao;
    }

    public void add(Room room) {
        try {
            dao.add(room);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void deleteById(long id) {
        try {
            dao.deleteById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public Room getById(long id) {
        Room room = null;
        try {
            room = dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
        return room;
    }

    public List<Room> getAll() {
        List<Room> rooms = null;
        try {
            rooms = dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
        return rooms;
    }
}
