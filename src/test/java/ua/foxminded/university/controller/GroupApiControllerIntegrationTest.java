package ua.foxminded.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.university.dto.GroupDTO;
import ua.foxminded.university.service.GroupService;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GroupApiControllerIntegrationTest {
    private static final String GROUP_NAME = "Group";
    private static final String INDEX_PATH = "/api/groups";
    private static final String ENTITY_PATH = "/api/groups/{id}";


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        GroupDTO group1 = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(10).build();
        GroupDTO group2 = GroupDTO.builder().id(2L).name(GROUP_NAME).maxStudents(20).build();
        GroupDTO group3 = GroupDTO.builder().id(3L).name(GROUP_NAME).maxStudents(30).build();
        service.save(group1);
        service.save(group2);
        service.save(group3);
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(group1, group2, group3))));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(10).build();
        service.save(group);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, group.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(10).build();
        service.save(group);
        mockMvc.perform(
                MockMvcRequestBuilders.post(INDEX_PATH)
                        .content(objectMapper.writeValueAsString(group))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(group)));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(10).build();
        GroupDTO updatedGroup = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(24).build();
        service.save(group);

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, group.getId())
                .content(objectMapper.writeValueAsString(updatedGroup))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(GROUP_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxStudents", Matchers.is(24)));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(10).build();
        service.save(group);
        mockMvc.perform
                (MockMvcRequestBuilders.delete(ENTITY_PATH, group.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
//