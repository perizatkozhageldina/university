package ua.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.model.Room;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class RoomJdbcDAOTest {
    private static final Room expectedRoom1 = Room.builder().roomId(101).capacity(1100).build();
    private static final Room expectedRoom2 = Room.builder().roomId(102).capacity(1200).build();    
    private static final Room expectedRoom3 = Room.builder().roomId(103).capacity(1300).build();
    
    @Autowired
    private RoomJdbcDAO dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddRoom_whenAddMethodCalled() throws DAOException {
        dao.add(expectedRoom1);
        Room actualRoom = dao.getById(expectedRoom1.getRoomId());
        assertEquals(expectedRoom1, actualRoom);        
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteRoom_whenDeleteMethodCalled() throws DAOException {
        dao.add(expectedRoom1);
        dao.deleteById(expectedRoom1.getRoomId());
        Room actualRoom = dao.getById(expectedRoom1.getRoomId());
        assertNull(actualRoom);    
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAudience_whenGetByIdMethodCalled() throws DAOException {
        dao.add(expectedRoom1);
        Room actualRoom = dao.getById(expectedRoom1.getRoomId());
        assertEquals(expectedRoom1, actualRoom);        
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGettAllAudiences_whenGetAllMethodCalled() throws DAOException {
        List<Room> expectedRooms = new ArrayList<>();
        expectedRooms.add(expectedRoom1);
        expectedRooms.add(expectedRoom2);
        expectedRooms.add(expectedRoom3);
        dao.add(expectedRoom1);
        dao.add(expectedRoom2);
        dao.add(expectedRoom3);
        List<Room> actualRooms = dao.getAll();
        assertEquals(expectedRooms, actualRooms);        
    }
}