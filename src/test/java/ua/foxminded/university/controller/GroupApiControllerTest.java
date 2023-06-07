package ua.foxminded.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.GroupService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class GroupApiControllerTest {
    private static final String GROUP_NAME = "Group";
    private static final String INDEX_PATH = "/api/groups";
    private static final String ENTITY_PATH = "/api/groups/{id}";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private GroupService groupService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .dispatchOptions(true).build();
    }

    @Test
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        Group group1 = Group.builder().id(1L).name(GROUP_NAME).maxStudents(10).build();
        Group group2 = Group.builder().id(2L).name(GROUP_NAME).maxStudents(20).build();
        Group group3 = Group.builder().id(3L).name(GROUP_NAME).maxStudents(30).build();

        groupService.save(group1);
        groupService.save(group2);
        groupService.save(group3);

        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(GROUP_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].maxStudents", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(GROUP_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].maxStudents", Matchers.is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is(GROUP_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].maxStudents", Matchers.is(30)));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        Group group1 = Group.builder().id(1L).name(GROUP_NAME).maxStudents(10).build();
        Group group2 = Group.builder().id(2L).name(GROUP_NAME).maxStudents(20).build();
        Group group3 = Group.builder().id(3L).name(GROUP_NAME).maxStudents(30).build();

        groupService.save(group1);
        groupService.save(group2);
        groupService.save(group3);

        mockMvc.perform(MockMvcRequestBuilders.get(ENTITY_PATH, group1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'id': 1}"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        Group group = Group.builder().id(1L).name(GROUP_NAME).maxStudents(10).build();

        mockMvc.perform(MockMvcRequestBuilders.post(INDEX_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(GROUP_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxStudents", Matchers.is(10)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        Group savedGroup = Group.builder().id(1L).name(GROUP_NAME).maxStudents(10).build();
        groupService.save(savedGroup);

        Group updatedGroup = Group.builder().id(1L).name("Updated Group").maxStudents(10).build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, savedGroup.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedGroup)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated Group")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxStudents", Matchers.is(10)));
    }

    @Test
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        Group group = Group.builder().id(1L).name(GROUP_NAME).maxStudents(10).build();
        groupService.save(group);
        mockMvc.perform(MockMvcRequestBuilders.delete(ENTITY_PATH, group.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private String asJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
