package ua.foxminded.university.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
class RoomControllerTest {
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
    void shouldReturnIndexView_whenListMethodExecuted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(INDEX_ATTRIBUTE));
    }

    @Test
    void shouldReturnAddView_whenAddMethodExecuted() throws Exception {
        Room room = Room.builder().id(1L).name(ROOM_NAME).capacity(10).build();
        roomService.save(room);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(ROOM_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        Room room = Room.builder().id(1L).name(ROOM_NAME).capacity(10).build();
        String encoded = URLEncoder.encode(room.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(ROOM_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        Room room = Room.builder().id(1L).name(ROOM_NAME).capacity(10).build();
        roomService.save(room);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Room")
                .param("capacity", "10"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        Room room = Room.builder().id(1L).name(ROOM_NAME).capacity(10).build();
        roomService.save(room);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, room.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(ROOM_ATTRIBUTE));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        Room room = Room.builder().id(1L).name(ROOM_NAME).capacity(10).build();
        roomService.save(room);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, room.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));

    }
}
