package ua.foxminded.university.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.repository.RoomJdbcRepository;

import javax.validation.Valid;

@Service
@Validated
public class RoomService {
    private RoomJdbcRepository dao;

    @Autowired
    public RoomService(RoomJdbcRepository dao) {
        this.dao = dao;
    }

    public Room save(@Valid Room room) throws ServiceException {
        return dao.save(room);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Optional<Room> getById(long id) throws ServiceException {
        return dao.findById(id);
    }

    public List<Room> getAll() throws ServiceException {
        return dao.findAll();
    }
}