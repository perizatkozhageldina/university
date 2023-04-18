package ua.foxminded.university.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.service.RoomService;

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
    private static final String ROOM_PARAM = "Lecture";

    private MockMvc mockMvc;

    @Mock
    private RoomService mockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new RoomController(mockService)).build();
    }

    @Test
    void shouldReturnIndexView_whenListMethodExecuted() throws Exception {
        List<Room> roomList = Arrays.asList(Room.builder().id(1L).capacity(10).build(),
                Room.builder().id(2L).capacity(20).build(), Room.builder().id(3L).capacity(30).build());

        Mockito.when(mockService.getAll()).thenReturn(roomList);

        mockMvc.perform(get(INDEX_PATH)).andExpect(status().isOk()).andExpect(view().name(INDEX_VIEW))
                .andExpect(model().attributeExists(INDEX_ATTRIBUTE));
    }

    @Test
    void shouldReturnAddView_whenAddMethodExecuted() throws Exception {
        Room room = Room.builder().id(1L).capacity(10).build();

        Mockito.when(mockService.add(room)).thenReturn(true);

        mockMvc.perform(get(ADD_PATH)).andExpect(status().isOk()).andExpect(view().name(ADD_VIEW))
                .andExpect(model().attributeExists(ROOM_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        Room room = Room.builder().id(1L).capacity(10).build();
        String encoded = URLEncoder.encode(room.toString(), "UTF-8");

        Mockito.when(mockService.add(room)).thenReturn(true);

        mockMvc.perform(post(SAVE_PATH).param(ROOM_PARAM, encoded)).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        Room room = Room.builder().id(1L).capacity(10).build();
        String encoded = URLEncoder.encode(room.toString(), "UTF-8");

        Mockito.when(mockService.update(room)).thenReturn(true);

        mockMvc.perform(patch(UPDATE_PATH).param(ROOM_PARAM, encoded)).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        Room room = Room.builder().id(1L).capacity(10).build();

        Mockito.when(mockService.getById(room.getId())).thenReturn(room);

        mockMvc.perform(get(EDIT_PATH, "1")).andExpect(status().isOk()).andExpect(view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(ROOM_ATTRIBUTE));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        Room room = Room.builder().id(1L).capacity(10).build();

        Mockito.when(mockService.deleteById(room.getId())).thenReturn(true);

        mockMvc.perform(delete(DELETE_PATH, "1")).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }
}
