package ua.foxminded.university.service;

import java.util.Collections;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);
    private RoomJdbcDAO dao;

    @Autowired
    public RoomService(RoomJdbcDAO dao) {
        this.dao = dao;
    }

    public boolean add(Room room) {
        try {
            dao.add(room);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return true;
        }
    }
    
    public boolean update(Room room) {
        try {
            dao.update(room);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return true;
        }
    }

    public boolean deleteById(long id) {
        try {
            dao.deleteById(id);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return true;
        }
    }

    public Room getById(long id) {
        try {
            return dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Room> getAll() {
        try {
            return dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
