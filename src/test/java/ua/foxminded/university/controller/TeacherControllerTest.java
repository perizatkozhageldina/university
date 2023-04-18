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
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.TeacherService;

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

    private MockMvc mockMvc;

    @Mock
    private TeacherService mockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new TeacherController(mockService)).build();
    }

    @Test
    void shouldReturnIndexView_whenListMethodExecuted() throws Exception {
        List<Teacher> teacherList = Arrays.asList(Teacher.builder().id(1L).category("Junior").hours(12).build(),
                Teacher.builder().id(2L).category("Middle").hours(24).build(),
                Teacher.builder().id(3L).category("Senior").hours(36).build());

        Mockito.when(mockService.getAll()).thenReturn(teacherList);

        mockMvc.perform(get(INDEX_PATH)).andExpect(status().isOk()).andExpect(view().name(INDEX_VIEW))
                .andExpect(model().attributeExists(INDEX_ATTRIBUTE));
    }

    @Test
    void shouldReturnAddView_whenAddMethodExecuted() throws Exception {
        Teacher teacher = Teacher.builder().id(1L).category("Junior").hours(12).build();

        Mockito.when(mockService.add(teacher)).thenReturn(true);

        mockMvc.perform(get(ADD_PATH)).andExpect(status().isOk()).andExpect(view().name(ADD_VIEW))
                .andExpect(model().attributeExists(TEACHER_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        Teacher teacher = Teacher.builder().id(1L).category("Junior").hours(12).build();

        String encoded = URLEncoder.encode(teacher.toString(), "UTF-8");

        Mockito.when(mockService.add(teacher)).thenReturn(true);

        mockMvc.perform(post(SAVE_PATH).param(TEACHER_PARAM, encoded)).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        Teacher teacher = Teacher.builder().id(1L).category("Junior").hours(12).build();

        String encoded = URLEncoder.encode(teacher.toString(), "UTF-8");

        Mockito.when(mockService.update(teacher)).thenReturn(true);

        mockMvc.perform(patch(UPDATE_PATH).param(TEACHER_PARAM, encoded)).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        Teacher teacher = Teacher.builder().id(1L).category("Junior").hours(12).build();

        Mockito.when(mockService.getById(teacher.getId())).thenReturn(teacher);

        mockMvc.perform(get(EDIT_PATH, "1")).andExpect(status().isOk()).andExpect(view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(TEACHER_ATTRIBUTE));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        Teacher teacher = Teacher.builder().id(1L).category("Junior").hours(12).build();

        Mockito.when(mockService.deleteById(teacher.getId())).thenReturn(true);

        mockMvc.perform(delete(DELETE_PATH, "1")).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }
}
