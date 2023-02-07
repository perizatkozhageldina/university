package ua.foxminded.university;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.service.RoomService;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        RoomService service = context.getBean(RoomService.class);        
        service.deleteById(145);
        service.add(Room.builder().roomId(145).capacity(25).build());
        context.close();
    }
}