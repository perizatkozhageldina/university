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
    private static final Room expectedRoom1 = Room.builder().id(101).capacity(1100).build();
    private static final Room expectedRoom2 = Room.builder().id(102).capacity(1200).build();
    private static final Room expectedRoom3 = Room.builder().id(103).capacity(1300).build();

    @Autowired
    private RoomJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddRoom_whenAddMethodCalled() {
        dao.save(expectedRoom1);
        Optional<Room> actualRoom = dao.findById(expectedRoom1.getId());
        assertEquals(expectedRoom1, actualRoom);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteRoom_whenDeleteMethodCalled() {
        dao.save(expectedRoom1);
        dao.deleteById(expectedRoom1.getId());
        Optional<Room> actualRoom = dao.findById(expectedRoom1.getId());
        assertNull(actualRoom);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAudience_whenGetByIdMethodCalled() {
        dao.save(expectedRoom1);
        Optional<Room> actualRoom = dao.findById(expectedRoom1.getId());
        assertEquals(expectedRoom1, actualRoom);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGettAllAudiences_whenGetAllMethodCalled() {
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