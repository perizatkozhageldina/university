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
import ua.foxminded.university.dto.StudentDTO;
import ua.foxminded.university.service.StudentService;

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerIntegrationTest {
    private static final String INDEX_PATH = "/students";
    private static final String ADD_PATH = "/students/add";
    private static final String SAVE_PATH = "/students/save";
    private static final String UPDATE_PATH = "/students/update";
    private static final String EDIT_PATH = "/students/{id}/edit";
    private static final String DELETE_PATH = "/students/{id}/delete";
    private static final String INDEX_VIEW = "student/index";
    private static final String ADD_VIEW = "student/add";
    private static final String EDIT_VIEW = "student/edit";
    private static final String REDIRECT_VIEW = "redirect:/students";
    private static final String INDEX_ATTRIBUTE = "students";
    private static final String STUDENT_ATTRIBUTE = "student";
    private static final String STUDENT_PARAM = "Student";
    private static final String STUDENT_NAME = "Name";
    private static final String STUDENT_SURNAME = "Surname";

    @Autowired
    private StudentService service;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

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
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        service.save(student);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(STUDENT_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        String encoded = URLEncoder.encode(student.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(STUDENT_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        service.save(student);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Name")
                .param("surname", "Updated Surname")
                .param("academicYear", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        service.save(student);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, student.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(STUDENT_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        service.save(student);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, student.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }
}//