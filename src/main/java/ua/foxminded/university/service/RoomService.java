package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
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

    public boolean add(Room room) throws ServiceException {
        try {
            dao.add(room);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
    
    public boolean update(Room room) throws ServiceException {
        try {
            dao.update(room);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteById(long id) throws ServiceException {
        try {
            dao.deleteById(id);
            return true;
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public Room getById(long id) throws ServiceException {
        try {
            return dao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Room> getAll() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
