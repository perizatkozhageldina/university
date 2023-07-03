package ua.foxminded.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.service.RoomService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class RoomApiControllerTest {
    private static final String ROOM_NAME = "Room";
    private static final String INDEX_PATH = "/api/rooms";
    private static final String ENTITY_PATH = "/api/rooms/{id}";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .dispatchOptions(true).build();
    }

    @Test
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(room);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, room.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(room);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, room.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(room);
        mockMvc.perform(
                MockMvcRequestBuilders.post(INDEX_PATH)
                        .content(objectMapper.writeValueAsString(room))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(room)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        RoomDTO updatedRoom = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(24).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(room);
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(room);

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, room.getId())
                .content(objectMapper.writeValueAsString(updatedRoom))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(ROOM_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity", Matchers.is(24)));
    }

    @Test
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(1).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(room);
        mockMvc.perform
                (MockMvcRequestBuilders.delete(ENTITY_PATH, room.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
//