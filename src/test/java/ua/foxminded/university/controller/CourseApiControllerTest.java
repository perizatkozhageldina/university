package ua.foxminded.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ua.foxminded.university.service.CourseService;

import java.util.Arrays;

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
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService service;

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
        Mockito.when(service.getAll()).thenReturn(Arrays.asList(course1, course2, course3));
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(course1, course2, course3))));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(course);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, course.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(course);
        mockMvc.perform(
                MockMvcRequestBuilders.post(INDEX_PATH)
                        .content(objectMapper.writeValueAsString(course))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(course)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        CourseDTO courseUpdated = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(24).build();

        Mockito.when(service.save(Mockito.any())).thenReturn(course);
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(course);

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, course.getId())
                .content(objectMapper.writeValueAsString(courseUpdated))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(COURSE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hours", Matchers.is(24)));
    }

    @Test
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(course);
        mockMvc.perform
                (MockMvcRequestBuilders.delete(ENTITY_PATH, course.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}