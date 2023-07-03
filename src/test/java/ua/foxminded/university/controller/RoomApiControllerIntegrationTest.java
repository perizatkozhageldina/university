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
import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.service.RoomService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomApiControllerIntegrationTest {
    private static final String ROOM_NAME = "Room";
    private static final String INDEX_PATH = "/api/rooms";
    private static final String ENTITY_PATH = "/api/rooms/{id}";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        service.save(room);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, room.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        service.save(room);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, room.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        service.save(room);
        mockMvc.perform(
                MockMvcRequestBuilders.post(INDEX_PATH)
                        .content(objectMapper.writeValueAsString(room))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(room)));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        RoomDTO updatedRoom = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(24).build();
        service.save(room);

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, room.getId())
                .content(objectMapper.writeValueAsString(updatedRoom))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(ROOM_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity", Matchers.is(24)));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        service.save(room);
        mockMvc.perform
                (MockMvcRequestBuilders.delete(ENTITY_PATH, room.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}