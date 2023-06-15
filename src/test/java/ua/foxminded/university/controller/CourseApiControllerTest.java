package ua.foxminded.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import ua.foxminded.university.model.Course;
import ua.foxminded.university.service.CourseService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class CourseApiControllerTest {
    private static final String COURSE_NAME = "Course";
    private static final String INDEX_PATH = "/api/courses";
    private static final String ENTITY_PATH = "/api/courses/{id}";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CourseService courseService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .dispatchOptions(true).build();
    }

    @Test
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        CourseDTO course1 = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        CourseDTO course2 = CourseDTO.builder().id(2L).name(COURSE_NAME).level(2).hours(24).build();
        CourseDTO course3 = CourseDTO.builder().id(3L).name(COURSE_NAME).level(3).hours(36).build();

        courseService.save(course1);
        courseService.save(course2);
        courseService.save(course3);

        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(COURSE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].level", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].hours", Matchers.is(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(COURSE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].level", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].hours", Matchers.is(24)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is(COURSE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].level", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].hours", Matchers.is(36)));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        CourseDTO course1 = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        CourseDTO course2 = CourseDTO.builder().id(2L).name(COURSE_NAME).level(2).hours(24).build();
        CourseDTO course3 = CourseDTO.builder().id(3L).name(COURSE_NAME).level(3).hours(36).build();

        courseService.save(course1);
        courseService.save(course2);
        courseService.save(course3);

        mockMvc.perform(MockMvcRequestBuilders.get(ENTITY_PATH, course1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'id': 1}"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();

        mockMvc.perform(MockMvcRequestBuilders.post(INDEX_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(course)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(COURSE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hours", Matchers.is(12)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        CourseDTO savedCourse = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        courseService.save(savedCourse);

        CourseDTO updatedCourse = CourseDTO.builder().id(1L).name("Updated Course").level(1).hours(12).build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, savedCourse.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedCourse)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated Course")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hours", Matchers.is(12)));
    }

    @Test
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        courseService.save(course);
        mockMvc.perform(MockMvcRequestBuilders.delete(ENTITY_PATH, course.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private String asJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
