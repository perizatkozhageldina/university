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
import ua.foxminded.university.model.Room;
import ua.foxminded.university.service.RoomService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class RoomApiControllerTest {
    private static final String ROOM_NAME = "Room";
    private static final String INDEX_PATH = "/api/rooms";
    private static final String ENTITY_PATH = "/api/rooms/{id}";

    @Autowired
    WebApplicationContext wac;

    @Autowired
    RoomService roomService;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .dispatchOptions(true).build();
    }

    @Test
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        Room room1 = Room.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        Room room2 = Room.builder().id(2L).name(ROOM_NAME).capacity(2).build();
        Room room3 = Room.builder().id(3L).name(ROOM_NAME).capacity(3).build();

        roomService.save(room1);
        roomService.save(room2);
        roomService.save(room3);

        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(ROOM_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(ROOM_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].capacity", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is(ROOM_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].capacity", Matchers.is(3)));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        Room room1 = Room.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        Room room2 = Room.builder().id(2L).name(ROOM_NAME).capacity(2).build();
        Room room3 = Room.builder().id(3L).name(ROOM_NAME).capacity(3).build();

        roomService.save(room1);
        roomService.save(room2);
        roomService.save(room3);

        mockMvc.perform(MockMvcRequestBuilders.get(ENTITY_PATH, room1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'id': 1}"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        Room room = Room.builder().id(1L).name(ROOM_NAME).capacity(1).build();

        mockMvc.perform(MockMvcRequestBuilders.post(INDEX_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(room)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(ROOM_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity", Matchers.is(1)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        Room savedRoom = Room.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        roomService.save(savedRoom);

        Room updatedRoom = Room.builder().id(1L).name("Updated Room").capacity(1).build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, savedRoom.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedRoom)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated Room")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity", Matchers.is(1)));
    }

    @Test
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        Room room = Room.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        roomService.save(room);
        mockMvc.perform(MockMvcRequestBuilders.delete(ENTITY_PATH, room.getId()))
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
