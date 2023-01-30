package ua.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.foxminded.university.model.Audience;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class AudienceJdbcDAOTest extends DAOTest {

    @Autowired
    private static AudienceJdbcDAO dao;

    @Test
    @Sql("/testSchema.sql")
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
    }
}