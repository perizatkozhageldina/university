package ua.foxminded.university.dao;

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
import ua.foxminded.university.model.Audience;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class AudienceJdbcDAOTest {
    
    @Autowired
    private AudienceJdbcDAO dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddAudience_whenAddMethodCalled() {
        Audience expectedAudience = Audience.builder().audienceId(102).room(555).capacity(1200).build();
        dao.add(expectedAudience);
        Audience actualAudience = dao.getById(expectedAudience.getAudienceId());
        assertEquals(expectedAudience, actualAudience);        
    }
    
//    @Test
//    @Sql("classpath:testSchema.sql")
//    void shouldDeleteAudience_whenDeleteMethodCalled() {
//        Audience audience = Audience.builder().audienceId(102).room(555).capacity(1200).build();
//        dao.add(audience);
//        dao.deleteById(audience.getAudienceId());
//        Audience actualAudience = dao.getById(audience.getAudienceId());
//        assertEquals(null, actualAudience);        
//    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAudience_whenGetByIdMethodCalled() {
        Audience expectedAudience = Audience.builder().audienceId(102).room(555).capacity(1200).build();
        dao.add(expectedAudience);
        Audience actualAudience = dao.getById(expectedAudience.getAudienceId());
        assertEquals(expectedAudience, actualAudience);        
    }
    
    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGettAllAudiences_whenGetAllMethodCalled() {
        List<Audience> expectedAudiences = new ArrayList<>();
        Audience audience1 = Audience.builder().audienceId(101).room(444).capacity(1100).build();
        Audience audience2 = Audience.builder().audienceId(102).room(555).capacity(1200).build();
        Audience audience3 = Audience.builder().audienceId(103).room(666).capacity(1300).build();
        expectedAudiences.add(audience1);
        expectedAudiences.add(audience2);
        expectedAudiences.add(audience3);
        dao.add(audience1);
        dao.add(audience2);
        dao.add(audience3);
        List<Audience> actualAudiences = dao.getAll();
        assertEquals(expectedAudiences, actualAudiences);        
    }
}