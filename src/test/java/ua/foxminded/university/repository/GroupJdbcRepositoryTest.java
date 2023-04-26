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
    private static final Group expectedGroup1 = Group.builder().id(101).name("GROUP1").build();
    private static final Group expectedGroup2 = Group.builder().id(102).name("GROUP2").build();
    private static final Group expectedGroup3 = Group.builder().id(103).name("GROUP3").build();

    @Autowired
    private GroupJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddGroup_whenAddMethodCalled() throws RepositoryException {
        dao.add(expectedGroup1);
        Group actualGroup = dao.getById(expectedGroup1.getId());
        assertEquals(expectedGroup1, actualGroup);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldDeleteGroup_whenDeleteMethodCalled() throws RepositoryException {
        dao.add(expectedGroup1);
        dao.deleteById(expectedGroup1.getId());
        Group actualGroup = dao.getById(expectedGroup1.getId());
        assertNull(actualGroup);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGetGroup_whenGetByIdMethodCalled() throws RepositoryException {
        dao.add(expectedGroup1);
        Group actualGroup = dao.getById(expectedGroup1.getId());
        assertEquals(expectedGroup1, actualGroup);
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldGettAllGroups_whenGetAllMethodCalled() throws RepositoryException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(expectedGroup1);
        expectedGroups.add(expectedGroup2);
        expectedGroups.add(expectedGroup3);
        dao.add(expectedGroup1);
        dao.add(expectedGroup2);
        dao.add(expectedGroup3);
        List<Group> actualGroups = dao.getAll();
        assertEquals(expectedGroups, actualGroups);
    }
}