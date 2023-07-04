package ua.foxminded.university.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.foxminded.university.dto.GroupDTO;
import ua.foxminded.university.repository.GroupJdbcRepository;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GroupServiceIntegrationTest {

    @Autowired
    private GroupService service;

    @Autowired
    GroupJdbcRepository dao;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldAddAndGetGroup_whenServiceAddAndGetMethodsCalled() {
        GroupDTO group = GroupDTO.builder().id(1L).name("Group").maxStudents(10).build();
        GroupDTO createdGroup = service.save(group);
        GroupDTO retrievedGroup = service.getById(createdGroup.getId());
        Assertions.assertNotNull(retrievedGroup);
        Assertions.assertEquals(group.getName(), retrievedGroup.getName());
        Assertions.assertEquals(group.getMaxStudents(), retrievedGroup.getMaxStudents());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldGetAllGroups_whenGetAllMethodCalled() {
        GroupDTO group1 = GroupDTO.builder().id(1L).name("Group1").maxStudents(10).build();
        GroupDTO group2 = GroupDTO.builder().id(2L).name("Group2").maxStudents(10).build();
        service.save(group1);
        service.save(group2);

        List<GroupDTO> allGroups = service.getAll();
        Assertions.assertEquals(2, allGroups.size());
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteGroup_whenDeleteMethodCalled() {
        GroupDTO group = GroupDTO.builder().id(1L).name("Group").maxStudents(10).build();
        GroupDTO savedGroup = service.save(group);
        service.deleteById(savedGroup.getId());
        Assertions.assertFalse(dao.existsById(savedGroup.getId()));
    }
}