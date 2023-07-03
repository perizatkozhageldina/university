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
import ua.foxminded.university.dto.GroupDTO;
import ua.foxminded.university.service.GroupService;

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerIntegrationTest {
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

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupService service;

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
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(20).build();
        service.save(group);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(GROUP_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(20).build();
        String encoded = URLEncoder.encode(group.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(GROUP_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(20).build();
        service.save(group);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Group")
                .param("maxStudents", "20"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(20).build();
        service.save(group);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, group.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(GROUP_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        GroupDTO group = GroupDTO.builder().id(1L).name(GROUP_NAME).maxStudents(20).build();
        service.save(group);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, group.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }
}//