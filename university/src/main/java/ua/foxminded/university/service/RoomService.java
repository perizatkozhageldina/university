package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.university.dao.RoomJdbcDAO;
import ua.foxminded.university.model.Room;

public class RoomService {

    @Autowired
    private RoomJdbcDAO dao;
    
    public void addRoom(Room room) {
        dao.add(room);
    }
    
    public void deleteRoom(Room room) {
        dao.deleteById(room.getRoomId());
    }
    
    public Room getRoomById(int id) {
        return dao.getById(id);
    }
    
    public List<Room> getAllRooms() {
        return dao.getAll();
    }
}
