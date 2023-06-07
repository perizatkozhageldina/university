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
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.StudentService;

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
class StudentControllerTest {
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
    private WebApplicationContext wac;

    @Autowired
    private StudentService studentService;

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
        Student student = Student.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        studentService.save(student);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(STUDENT_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        Student student = Student.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        String encoded = URLEncoder.encode(student.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(STUDENT_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        Student student = Student.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        studentService.save(student);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Name")
                .param("surname", "Updated Surname")
                .param("academicYear", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        Student student = Student.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        studentService.save(student);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, student.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(STUDENT_ATTRIBUTE));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        Student student = Student.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        studentService.save(student);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, student.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));

    }
}