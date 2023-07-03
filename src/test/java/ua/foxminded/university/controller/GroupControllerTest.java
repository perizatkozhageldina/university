package ua.foxminded.university.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.dto.GroupDTO;
import ua.foxminded.university.service.GroupService;

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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
    private static final String GROUP_NAME = "Group";

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private GroupService service;

    private MockMvc mockMvc;

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
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(20).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(group);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(GROUP_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(20).build();
        String encoded = URLEncoder.encode(group.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(GROUP_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(20).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(group);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Group")
                .param("maxStudents", "20"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(20).build();
        Mockito.when(service.getById(group.getId())).thenReturn(group);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, group.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(GROUP_ATTRIBUTE));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(20).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(group);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, group.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }
}//