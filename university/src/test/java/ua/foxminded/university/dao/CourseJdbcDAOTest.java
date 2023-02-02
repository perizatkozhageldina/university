package ua.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.university.model.Course;

@ContextConfiguration(classes = CourseJdbcDAO.class) 
class CourseJdbcDAOTest {
    private static final Course expectedCourse = Course.builder().courseId(1234).hours(1200).build();

    @Autowired
    CourseJdbcDAO dao;
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddRoom_whenAddMethodCalled() {
        dao.add(expectedCourse);
        Course actualCourse = dao.getById(expectedCourse.getCourseId());
        assertEquals(expectedCourse, actualCourse);        
    }
    
//    @Test
//    @Sql("classpath:testSchema.sql")
//    void shouldDeleteRoom_whenDeleteMethodCalled() {
//        Room room = Room.builder().roomId(102).capacity(1200).build();
//        dao.add(room);
//        dao.deleteById(room.getRoomId());
//        Room actualRoom = dao.getById(room.getRoomId());
//        assertEquals(null, actualRoom);        
//    }
//    
//    @Test
//    @Sql("classpath:testSchema.sql")
//    void shouldGetAudience_whenGetByIdMethodCalled() {
//        Room expectedRoom = Room.builder().roomId(102).capacity(1200).build();
//        dao.add(expectedRoom);
//        Room actualRoom = dao.getById(expectedRoom.getRoomId());
//        assertEquals(expectedRoom, actualRoom);        
//    }
//    
//    @Test
//    @Sql("classpath:testSchema.sql")
//    void shouldGettAllAudiences_whenGetAllMethodCalled() {
//        List<Room> expectedRooms = new ArrayList<>();
//        Room room1 = Room.builder().roomId(101).capacity(1100).build();
//        Room room2 = Room.builder().roomId(102).capacity(1200).build();
//        Room room3 = Room.builder().roomId(103).capacity(1300).build();
//        expectedRooms.add(room1);
//        expectedRooms.add(room2);
//        expectedRooms.add(room3);
//        dao.add(room1);
//        dao.add(room2);
//        dao.add(room3);
//        List<Room> actualRooms = dao.getAll();
//        assertEquals(expectedRooms, actualRooms);        
//    }

}
