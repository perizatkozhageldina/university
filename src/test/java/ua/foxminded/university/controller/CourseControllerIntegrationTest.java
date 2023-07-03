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
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.service.CourseService;

import java.net.URLEncoder;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerIntegrationTest {
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

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService service;

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
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        service.save(course);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(COURSE_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        String encoded = URLEncoder.encode(course.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(COURSE_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        service.save(course);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Course")
                .param("level", "1")
                .param("hours", "12"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        service.save(course);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, course.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        service.save(course);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, course.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }
}
//