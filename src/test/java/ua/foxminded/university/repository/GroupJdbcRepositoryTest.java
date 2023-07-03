package ua.foxminded.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.model.Group;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class GroupJdbcRepositoryTest {
    private static final Group EXPECTED_GROUP_1 = Group.builder().name("GROUP1").build();
    private static final Group EXPECTED_GROUP_2 = Group.builder().name("GROUP2").build();
    private static final Group EXPECTED_GROUP_3 = Group.builder().name("GROUP3").build();

    @Autowired
    private GroupJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddGroup_whenAddMethodCalled() {
        Group savedGroup = dao.save(EXPECTED_GROUP_1);
        assertEquals(EXPECTED_GROUP_1, savedGroup);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteGroup_whenDeleteMethodCalled() {
        Group savedGroup = dao.save(EXPECTED_GROUP_1);
        dao.deleteById(savedGroup.getId());
        boolean exists = dao.existsById(savedGroup.getId());
        assertFalse(exists);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetGroup_whenGetByIdMethodCalled() {
        dao.save(EXPECTED_GROUP_1);
        Group actualGroup = dao.findById(EXPECTED_GROUP_1.getId()).orElse(null);
        assertEquals(EXPECTED_GROUP_1, actualGroup);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetAllGroups_whenGetAllMethodCalled() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(EXPECTED_GROUP_1);
        expectedGroups.add(EXPECTED_GROUP_2);
        expectedGroups.add(EXPECTED_GROUP_3);
        dao.save(EXPECTED_GROUP_1);
        dao.save(EXPECTED_GROUP_2);
        dao.save(EXPECTED_GROUP_3);
        List<Group> actualGroups = dao.findAll();
        assertEquals(expectedGroups, actualGroups);
    }
}
//