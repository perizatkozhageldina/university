package ua.foxminded.university;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.dao.RoomJdbcDAO;
import ua.foxminded.university.model.Room;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        RoomJdbcDAO dao = context.getBean(RoomJdbcDAO.class);        
        dao.deleteById(100);
        dao.add(Room.builder().roomId(145).capacity(25).build());
        context.close();
    }
}