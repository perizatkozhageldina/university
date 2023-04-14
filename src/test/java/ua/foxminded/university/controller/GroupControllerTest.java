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
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.GroupService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
class GroupControllerTest {
    private static final String INDEX_PATH = "/groups";
    private static final String ADD_PATH = "/groups/add";
    private static final String SAVE_PATH = "/groups/save";
    private static final String UPDATE_PATH = "/groups/update";
    private static final String EDIT_PATH = "/groups/{id}/edit";
    private static final String DELETE_PATH = "/groups/{id}/delete";
    private static final String INDEX_VIEW = "group/index";
    private static final String ADD_VIEW = "group/add";
    private static final String EDIT_VIEW = "group/edit";
    private static final String REDIRECT_VIEW = "redirect:/groups";
    private static final String INDEX_ATTRIBUTE = "groups";
    private static final String GROUP_ATTRIBUTE = "group";
    private static final String GROUP_PARAM = "Group";

    private MockMvc mockMvc;

    @Mock
    private GroupService mockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new GroupController(mockService)).build();
    }

    @Test
    void shouldReturnIndexView_whenListMethodExecuted() throws Exception {
        List<Group> groupList = Arrays.asList(Group.builder().id(1L).name("VT-11").build(),
                Group.builder().id(2L).name("VT-21").build(), Group.builder().id(3L).name("VT-31").build());

        Mockito.when(mockService.getAll()).thenReturn(groupList);

        mockMvc.perform(get(INDEX_PATH)).andExpect(status().isOk()).andExpect(view().name(INDEX_VIEW))
                .andExpect(model().attributeExists(INDEX_ATTRIBUTE));
    }

    @Test
    void shouldReturnAddView_whenAddMethodExecuted() throws Exception {
        Group group = Group.builder().id(1L).name("VT-11").build();

        Mockito.when(mockService.add(group)).thenReturn(true);

        mockMvc.perform(get(ADD_PATH)).andExpect(status().isOk()).andExpect(view().name(ADD_VIEW))
                .andExpect(model().attributeExists(GROUP_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        Group group = Group.builder().id(1L).name("VT-11").build();
        String encoded = URLEncoder.encode(group.toString(), "UTF-8");

        Mockito.when(mockService.add(group)).thenReturn(true);

        mockMvc.perform(post(SAVE_PATH).param(GROUP_PARAM, encoded)).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        Group group = Group.builder().id(1L).name("VT-11").build();
        String encoded = URLEncoder.encode(group.toString(), "UTF-8");

        Mockito.when(mockService.update(group)).thenReturn(true);

        mockMvc.perform(patch(UPDATE_PATH).param(GROUP_PARAM, encoded)).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        Group group = Group.builder().id(1L).name("VT-11").build();

        Mockito.when(mockService.getById(group.getId())).thenReturn(group);

        mockMvc.perform(get(EDIT_PATH, "1")).andExpect(status().isOk()).andExpect(view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(GROUP_ATTRIBUTE));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        Group group = Group.builder().id(1L).name("VT-11").build();

        Mockito.when(mockService.deleteById(group.getId())).thenReturn(true);

        mockMvc.perform(delete(DELETE_PATH, "1")).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }
}