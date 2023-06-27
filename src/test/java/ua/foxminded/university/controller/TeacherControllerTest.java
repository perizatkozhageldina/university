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
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.service.TeacherService;

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
class TeacherControllerTest {
    private static final String INDEX_PATH = "/teachers";
    private static final String ADD_PATH = "/teachers/add";
    private static final String SAVE_PATH = "/teachers/save";
    private static final String UPDATE_PATH = "/teachers/update";
    private static final String EDIT_PATH = "/teachers/{id}/edit";
    private static final String DELETE_PATH = "/teachers/{id}/delete";
    private static final String INDEX_VIEW = "teacher/index";
    private static final String ADD_VIEW = "teacher/add";
    private static final String EDIT_VIEW = "teacher/edit";
    private static final String REDIRECT_VIEW = "redirect:/teachers";
    private static final String INDEX_ATTRIBUTE = "teachers";
    private static final String TEACHER_ATTRIBUTE = "teacher";
    private static final String TEACHER_PARAM = "Teacher";
    private static final String TEACHER_NAME = "Name";
    private static final String TEACHER_SURNAME = "Surname";
    private static final String TEACHER_CATEGORY = "Category";

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private TeacherService service;

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
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).category(TEACHER_CATEGORY).hours(12).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(teacher);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(TEACHER_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).category(TEACHER_CATEGORY).hours(12).build();
        String encoded = URLEncoder.encode(teacher.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(TEACHER_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).category(TEACHER_CATEGORY).hours(12).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(teacher);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Name")
                .param("surname", "Updated Surname")
                .param("category", "Updated category")
                .param("hours", "12"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).category(TEACHER_CATEGORY).hours(12).build();
        Mockito.when(service.getById(teacher.getId())).thenReturn(teacher);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, teacher.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(TEACHER_ATTRIBUTE));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).category(TEACHER_CATEGORY).hours(12).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(teacher);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, teacher.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }
}
