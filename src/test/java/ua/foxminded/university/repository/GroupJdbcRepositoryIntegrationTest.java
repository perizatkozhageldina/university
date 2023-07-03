package ua.foxminded.university.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.university.model.Group;

import java.util.List;

@DataJpaTest
public class GroupJdbcRepositoryIntegrationTest {

    @Autowired
    private GroupJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldSaveGroup_whenSaveMethodCalled() {
        Group group = Group.builder().id(1L).name("Group").maxStudents(10).build();
        Group savedGroup = dao.save(group);
        Assert.assertEquals(savedGroup.getName(), "Group");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindById_whenFindByIdMethodCalled() {
        Group group = Group.builder().id(1L).name("Group").maxStudents(10).build();
        dao.save(group);
        Group foundGroup = dao.findById(group.getId()).orElse(null);
        Assert.assertNotNull(foundGroup);
        Assert.assertEquals(foundGroup.getName(),"Group");
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldFindAllGroups_whenFindAllMethodCalled() {
        Group group1 = Group.builder().id(1L).name("Group1").maxStudents(10).build();
        Group group2 = Group.builder().id(2L).name("Group2").maxStudents(20).build();
        dao.save(group1);
        dao.save(group2);
        List<Group> groups = dao.findAll();
        Assert.assertEquals(2, groups.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void testDeleteGroup() {
        Group group = Group.builder().id(1L).name("Group").maxStudents(10).build();
        Group savedGroup = dao.save(group);
        dao.deleteById(savedGroup.getId());
        Assert.assertNull(dao.findById(savedGroup.getId()).orElse(null));
    }
}
