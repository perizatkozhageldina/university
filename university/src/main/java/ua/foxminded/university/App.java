package ua.foxminded.university;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.dao.AudienceJdbcDAO;
import ua.foxminded.university.model.Audience;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        AudienceJdbcDAO dao = context.getBean(AudienceJdbcDAO.class);
        dao.add(Audience.builder().id(1).room(555).capacity(1200).build());
        context.close();
    }
}