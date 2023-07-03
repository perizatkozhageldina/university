package ua.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.service.RoomService;

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerIntegrationTest {
    private static final String INDEX_PATH = "/rooms";
    private static final String ADD_PATH = "/rooms/add";
    private static final String SAVE_PATH = "/rooms/save";
    private static final String UPDATE_PATH = "/rooms/update";
    private static final String EDIT_PATH = "/rooms/{id}/edit";
    private static final String DELETE_PATH = "/rooms/{id}/delete";
    private static final String INDEX_VIEW = "room/index";
    private static final String ADD_VIEW = "room/add";
    private static final String EDIT_VIEW = "room/edit";
    private static final String REDIRECT_VIEW = "redirect:/rooms";
    private static final String INDEX_ATTRIBUTE = "rooms";
    private static final String ROOM_ATTRIBUTE = "room";
    private static final String ROOM_PARAM = "Room";
    private static final String ROOM_NAME = "Room";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomService service;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnIndexView_whenListMethodExecuted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(INDEX_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnAddView_whenAddMethodExecuted() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(10).build();
        service.save(room);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(ROOM_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(10).build();
        String encoded = URLEncoder.encode(room.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(ROOM_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(10).build();
        service.save(room);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Room")
                .param("capacity", "10"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(10).build();
        service.save(room);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, room.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(ROOM_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        RoomDTO room = RoomDTO.builder().id(1L).name(ROOM_NAME).capacity(10).build();
        service.save(room);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, room.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }
}//