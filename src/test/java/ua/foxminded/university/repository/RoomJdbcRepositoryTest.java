package ua.foxminded.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final Room expectedRoom1 = Room.builder().name("Room1").capacity(1100).build();
    private static final Room expectedRoom2 = Room.builder().name("Room1").capacity(1200).build();
    private static final Room expectedRoom3 = Room.builder().name("Room1").capacity(1300).build();

    @Autowired
    private RoomJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddRoom_whenAddMethodCalled() {
        Room savedRoom = dao.save(expectedRoom1);
        assertEquals(expectedRoom1, savedRoom);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteRoom_whenDeleteMethodCalled() {
        Room savedRoom = dao.save(expectedRoom1);
        dao.deleteById(expectedRoom1.getId());
        boolean exists = dao.existsById(savedRoom.getId());
        assertFalse(exists);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAudience_whenGetByIdMethodCalled() {
        dao.save(expectedRoom1);
        Room actualRoom = dao.findById(expectedRoom1.getId()).orElse(null);
        assertEquals(expectedRoom1, actualRoom);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAllAudiences_whenGetAllMethodCalled() {
        List<Room> expectedRooms = new ArrayList<>();
        expectedRooms.add(expectedRoom1);
        expectedRooms.add(expectedRoom2);
        expectedRooms.add(expectedRoom3);
        dao.save(expectedRoom1);
        dao.save(expectedRoom2);
        dao.save(expectedRoom3);
        List<Room> actualRooms = dao.findAll();
        assertEquals(expectedRooms, actualRooms);
    }
}