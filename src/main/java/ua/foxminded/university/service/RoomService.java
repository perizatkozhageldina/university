package ua.foxminded.university.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.model.Room;
import ua.foxminded.university.repository.RoomJdbcRepository;

@Service
public class RoomService {
    private RoomJdbcRepository dao;

    @Autowired
    public RoomService(RoomJdbcRepository dao) {
        this.dao = dao;
    }

    public void save(Room room) throws ServiceException {
        dao.save(room);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Room getById(long id) throws ServiceException {
        return dao.findById(id).get();
    }

    public List<Room> getAll() throws ServiceException {
        return dao.findAll();
    }
}