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
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.StudentService;

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

    private MockMvc mockMvc;

    @Mock
    private StudentService mockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new StudentController(mockService)).build();
    }

    @Test
    void shouldReturnIndexView_whenListMethodExecuted() throws Exception {
        List<Student> studentList = Arrays.asList(Student.builder().id(1L).academicYear(1).build(),
                Student.builder().id(2L).academicYear(2).build(), Student.builder().id(3L).academicYear(3).build());

        Mockito.when(mockService.getAll()).thenReturn(studentList);

        mockMvc.perform(get(INDEX_PATH)).andExpect(status().isOk()).andExpect(view().name(INDEX_VIEW))
                .andExpect(model().attributeExists(INDEX_ATTRIBUTE));
    }

    @Test
    void shouldReturnAddView_whenAddMethodExecuted() throws Exception {
        Student student = Student.builder().id(1L).academicYear(1).build();

        mockMvc.perform(get(ADD_PATH)).andExpect(status().isOk()).andExpect(view().name(ADD_VIEW))
                .andExpect(model().attributeExists(STUDENT_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        Student student = Student.builder().id(1L).academicYear(1).build();
        String encoded = URLEncoder.encode(student.toString(), "UTF-8");

        mockMvc.perform(post(SAVE_PATH).param(STUDENT_PARAM, encoded)).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        Student student = Student.builder().id(1L).academicYear(1).build();
        String encoded = URLEncoder.encode(student.toString(), "UTF-8");

        mockMvc.perform(patch(UPDATE_PATH).param(STUDENT_PARAM, encoded)).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        Student student = Student.builder().id(1L).academicYear(1).build();

        Mockito.when(mockService.getById(student.getId())).thenReturn(student);

        mockMvc.perform(get(EDIT_PATH, "1")).andExpect(status().isOk()).andExpect(view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(STUDENT_ATTRIBUTE));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        Student student = Student.builder().id(1L).academicYear(1).build();

        mockMvc.perform(delete(DELETE_PATH, "1")).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }
}