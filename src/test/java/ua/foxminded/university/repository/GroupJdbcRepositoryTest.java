package ua.foxminded.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final Group expectedGroup1 = Group.builder().id(101).name("GROUP1").build();
    private static final Group expectedGroup2 = Group.builder().id(102).name("GROUP2").build();
    private static final Group expectedGroup3 = Group.builder().id(103).name("GROUP3").build();

    @Autowired
    private GroupJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddGroup_whenAddMethodCalled() {
        dao.save(expectedGroup1);
        Optional<Group> actualGroup = dao.findById(expectedGroup1.getId());
        assertEquals(expectedGroup1, actualGroup);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteGroup_whenDeleteMethodCalled() {
        dao.save(expectedGroup1);
        dao.deleteById(expectedGroup1.getId());
        Optional<Group> actualGroup = dao.findById(expectedGroup1.getId());
        assertNull(actualGroup);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetGroup_whenGetByIdMethodCalled() {
        dao.save(expectedGroup1);
        Optional<Group> actualGroup = dao.findById(expectedGroup1.getId());
        assertEquals(expectedGroup1, actualGroup);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGettAllGroups_whenGetAllMethodCalled() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(expectedGroup1);
        expectedGroups.add(expectedGroup2);
        expectedGroups.add(expectedGroup3);
        dao.save(expectedGroup1);
        dao.save(expectedGroup2);
        dao.save(expectedGroup3);
        List<Group> actualGroups = dao.findAll();
        assertEquals(expectedGroups, actualGroups);
    }
}
