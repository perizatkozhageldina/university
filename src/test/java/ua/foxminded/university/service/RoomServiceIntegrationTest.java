package ua.foxminded.university.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.repository.RoomJdbcRepository;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoomServiceIntegrationTest {

    @Autowired
    private RoomService service;

    @Autowired
    private RoomJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddAndGetRoom_whenServiceAddAndGetMethodsCalled() {
        RoomDTO room = RoomDTO.builder().id(1L).name("Room").capacity(10).build();
        RoomDTO createdRoom = service.save(room);
        RoomDTO retrievedRoom = service.getById(createdRoom.getId());
        assertNotNull(retrievedRoom);
        assertEquals(room.getName(), retrievedRoom.getName());
        assertEquals(room.getCapacity(), retrievedRoom.getCapacity());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldGetAllRooms_whenGetAllMethodCalled() {
        RoomDTO room1 = RoomDTO.builder().id(1L).name("Room").capacity(10).build();
        RoomDTO room2 = RoomDTO.builder().id(2L).name("Room").capacity(10).build();
        service.save(room1);
        service.save(room2);

        List<RoomDTO> allRooms = service.getAll();
        assertEquals(2, allRooms.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteRoom_whenDeleteMethodCalled() {
        RoomDTO room = RoomDTO.builder().id(1L).name("Lecture").capacity(10).build();
        RoomDTO savedLecture = service.save(room);
        service.deleteById(savedLecture.getId());
        assertFalse(dao.existsById(savedLecture.getId()));
    }
}