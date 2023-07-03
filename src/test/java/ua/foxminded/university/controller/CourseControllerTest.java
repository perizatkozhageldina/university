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
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.service.CourseService;

import java.net.URLEncoder;

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
    private static final String COURSE_NAME = "Course";

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private CourseService service;

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
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(course);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(COURSE_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        String encoded = URLEncoder.encode(course.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(COURSE_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(course);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Course")
                .param("level", "1")
                .param("hours", "12"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        Mockito.when(service.getById(course.getId())).thenReturn(course);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, course.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(course);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, course.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }
}//