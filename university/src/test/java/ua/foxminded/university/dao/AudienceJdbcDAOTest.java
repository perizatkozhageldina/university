package ua.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.university.config.TestConfig;
import ua.foxminded.university.model.Audience;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class AudienceJdbcDAOTest {
    
    @Autowired
    private AudienceJdbcDAO dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddAudience_whenAddMethodCalled() {
        Audience expectedAudience = Audience.builder().id(1).room(555).capacity(1200).build();
        dao.add(expectedAudience);
        Audience actualAudience = dao.getById(expectedAudience.getId());
        assertEquals(expectedAudience, actualAudience);
        
    }
}