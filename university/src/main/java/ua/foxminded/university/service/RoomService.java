package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.RoomJdbcDAO;
import ua.foxminded.university.model.Room;

@Service
public class RoomService implements GenericService<Room> {

    @Autowired
    private RoomJdbcDAO dao;
    
    public void add(Room room) {
        dao.add(room);
    }
    
    public void deleteById(int id) {
        dao.deleteById(id);
    }
    
    public Room getById(int id) {
        return dao.getById(id);
    }
    
    public List<Room> getAll() {
        return dao.getAll();
    }
}
