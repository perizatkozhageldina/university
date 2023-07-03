package ua.foxminded.university.repository;

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
class RoomJdbcRepositoryTest {
    private static final Room EXPECTED_ROOM_1 = Room.builder().name("Room1").capacity(1100).build();
    private static final Room EXPECTED_ROOM_2 = Room.builder().name("Room1").capacity(1200).build();
    private static final Room EXPECTED_ROOM_3 = Room.builder().name("Room1").capacity(1300).build();

    @Autowired
    private RoomJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddRoom_whenAddMethodCalled() {
        Room savedRoom = dao.save(EXPECTED_ROOM_1);
        assertEquals(EXPECTED_ROOM_1, savedRoom);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteRoom_whenDeleteMethodCalled() {
        Room savedRoom = dao.save(EXPECTED_ROOM_1);
        dao.deleteById(EXPECTED_ROOM_1.getId());
        boolean exists = dao.existsById(savedRoom.getId());
        assertFalse(exists);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAudience_whenGetByIdMethodCalled() {
        dao.save(EXPECTED_ROOM_1);
        Room actualRoom = dao.findById(EXPECTED_ROOM_1.getId()).orElse(null);
        assertEquals(EXPECTED_ROOM_1, actualRoom);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAllAudiences_whenGetAllMethodCalled() {
        List<Room> expectedRooms = new ArrayList<>();
        expectedRooms.add(EXPECTED_ROOM_1);
        expectedRooms.add(EXPECTED_ROOM_2);
        expectedRooms.add(EXPECTED_ROOM_3);
        dao.save(EXPECTED_ROOM_1);
        dao.save(EXPECTED_ROOM_2);
        dao.save(EXPECTED_ROOM_3);
        List<Room> actualRooms = dao.findAll();
        assertEquals(expectedRooms, actualRooms);
    }
}//