package ua.foxminded.university;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.service.CourseService;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CourseService service = context.getBean(CourseService.class);        
        service.deleteById(145);
        service.add(Course.builder().courseId(145).hours(24).build());
        context.close();
    }
}