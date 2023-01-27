package ua.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.config.TestConfig;
import ua.foxminded.university.model.Audience;

@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
class AudienceJdbcDAOTest {
    @Autowired
    private static AudienceJdbcDAO dao;
    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        dao = context.getBean(AudienceJdbcDAO.class);
    }

    @Test
    void shouldAddAudience_whenAddMethodCalled() {

        System.out.println("beginning");
        Audience expectedAudience = Audience.builder()
                .id(1)
                .room(545)
                .build();
        System.out.println(expectedAudience.getRoom());
        dao.add(expectedAudience);
        Audience actualAudience = dao.getById(expectedAudience.getId());
        System.out.println(actualAudience.getId());
        assertEquals(expectedAudience, actualAudience);
        context.close();
    }
}