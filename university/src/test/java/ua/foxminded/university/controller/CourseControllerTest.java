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
import ua.foxminded.university.model.Course;
import ua.foxminded.university.service.CourseService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
class CourseControllerTest {
    private static final String INDEX_PATH = "/courses";
    private static final String ADD_PATH = "/courses/add";
    private static final String SAVE_PATH = "/courses/save";
    private static final String UPDATE_PATH = "/courses/update";
    private static final String EDIT_PATH = "/courses/{id}/edit";
    private static final String DELETE_PATH = "/courses/{id}/delete";
    private static final String INDEX_VIEW = "course/index";
    private static final String ADD_VIEW = "course/add";
    private static final String EDIT_VIEW = "course/edit";
    private static final String REDIRECT_VIEW = "redirect:/courses";
    private static final String INDEX_ATTRIBUTE = "courses";
    private static final String COURSE_ATTRIBUTE = "course";
    private static final String COURSE_PARAM = "Course";
    
    private MockMvc mockMvc;

    @Mock
    private CourseService mockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new CourseController(mockService))
                .build();
    }

    @Test
    void shouldReturnIndexView_whenListMethodExecuted() throws Exception {
        List<Course> groupList = Arrays.asList(
                Course.builder()
                        .id(1L)
                        .level(1)
                        .hours(12)
                        .build(),
                Course.builder()
                        .id(2L)
                        .level(2)
                        .hours(24)
                        .build(),
                Course.builder()
                        .id(3L)
                        .level(3)
                        .hours(36)
                        .build());

        Mockito.when(mockService.getAll())
                .thenReturn(groupList);

        mockMvc.perform(get(INDEX_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(INDEX_VIEW))
                .andExpect(model().attributeExists(INDEX_ATTRIBUTE));
    }

    @Test
    void shouldReturnAddView_whenAddMethodExecuted() throws Exception {
        Course course = Course.builder()
                .id(1L)
                .level(1)
                .hours(12)
                .build();
        
        Mockito.when(mockService.add(course))
                .thenReturn(true);

        mockMvc.perform(get(ADD_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(ADD_VIEW))
                .andExpect(model().attributeExists(COURSE_ATTRIBUTE));
    }
    
    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        Course course = Course.builder()
                .id(1L)
                .level(1)
                .hours(12)
                .build();
        String encoded = URLEncoder.encode(course.toString(), "UTF-8");
        
        Mockito.when(mockService.add(course))
        .thenReturn(true);
        
        mockMvc.perform(post(SAVE_PATH)
        .param(COURSE_PARAM, encoded))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name(REDIRECT_VIEW));
    }
    
    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        Course course = Course.builder()
                .id(1L)
                .level(1)
                .hours(12)
                .build();
        String encoded = URLEncoder.encode(course.toString(), "UTF-8");
        
        Mockito.when(mockService.update(course))
        .thenReturn(true);
        
        mockMvc.perform(patch(UPDATE_PATH)
                .param(COURSE_PARAM, encoded))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name(REDIRECT_VIEW));
    }
    
    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        Course course = Course.builder()
                .id(1L)
                .level(1)
                .hours(12)
                .build();
        
        Mockito.when(mockService.getById(course.getId()))
        .thenReturn(course);
        
        mockMvc.perform(get(EDIT_PATH, "1"))
        .andExpect(status().isOk())
        .andExpect(view().name(EDIT_VIEW))
        .andExpect(model().attributeExists(COURSE_ATTRIBUTE));
    }
    
    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        Course course = Course.builder()
                .id(1L)
                .level(1)
                .hours(12)
                .build();
        
        Mockito.when(mockService.deleteById(course.getId()))
        .thenReturn(true);
        
        mockMvc.perform(delete(DELETE_PATH, "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name(REDIRECT_VIEW));
    }
}
