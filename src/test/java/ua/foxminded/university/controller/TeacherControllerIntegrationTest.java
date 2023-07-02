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
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.service.TeacherService;

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerIntegrationTest {
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

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherService service;

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
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).category(TEACHER_CATEGORY).hours(12).build();
        service.save(teacher);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(TEACHER_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).category(TEACHER_CATEGORY).hours(12).build();
        String encoded = URLEncoder.encode(teacher.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(TEACHER_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).category(TEACHER_CATEGORY).hours(12).build();
        service.save(teacher);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Name")
                .param("surname", "Updated Surname")
                .param("category", "Updated category")
                .param("hours", "12"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).category(TEACHER_CATEGORY).hours(12).build();
        service.save(teacher);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, teacher.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(TEACHER_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).category(TEACHER_CATEGORY).hours(12).build();
        service.save(teacher);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, teacher.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }
}