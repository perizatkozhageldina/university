package ua.foxminded.university.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.university.model.Room;

import java.util.List;

@DataJpaTest
public class RoomJdbcRepositoryIntegrationTest {

    @Autowired
    private RoomJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldSaveRoom_whenSaveMethodCalled() {
        Room room = Room.builder().id(1L).name("Room").capacity(10).build();
        Room savedRoom = dao.save(room);
        Assertions.assertEquals(savedRoom.getName(), "Room");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindById_whenFindByIdMethodCalled() {
        Room room = Room.builder().id(1L).name("Room").capacity(10).build();
        dao.save(room);
        Room foundRoom = dao.findById(room.getId()).orElse(null);
        Assertions.assertNotNull(foundRoom);
        Assertions.assertEquals(foundRoom.getName(),"Room");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindAllRooms_whenFindAllMethodCalled() {
        Room room1 = Room.builder().id(1L).name("Room1").capacity(10).build();
        Room room2 = Room.builder().id(2L).name("Room2").capacity(20).build();
        dao.save(room1);
        dao.save(room2);
        List<Room> rooms = dao.findAll();
        Assertions.assertEquals(2, rooms.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void testDeleteRoom() {
        Room room = Room.builder().id(1L).name("Room").capacity(10).build();
        Room savedRoom = dao.save(room);
        dao.deleteById(savedRoom.getId());
        Assertions.assertNull(dao.findById(savedRoom.getId()).orElse(null));
    }
}