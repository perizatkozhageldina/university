package ua.foxminded.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.service.CourseService;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseApiControllerIntegrationTest {
    private static final String COURSE_NAME = "Course";
    private static final String INDEX_PATH = "/api/courses";
    private static final String ENTITY_PATH = "/api/courses/{id}";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        CourseDTO course1 = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        CourseDTO course2 = CourseDTO.builder().id(2L).name(COURSE_NAME).level(2).hours(24).build();
        CourseDTO course3 = CourseDTO.builder().id(3L).name(COURSE_NAME).level(3).hours(36).build();
        service.save(course1);
        service.save(course2);
        service.save(course3);
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(course1, course2, course3))));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        service.save(course);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, course.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        service.save(course);
        mockMvc.perform(
                MockMvcRequestBuilders.post(INDEX_PATH)
                        .content(objectMapper.writeValueAsString(course))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(course)));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        CourseDTO courseUpdated = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(24).build();

        service.save(course);

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
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        CourseDTO course = CourseDTO.builder().id(1L).name(COURSE_NAME).level(1).hours(12).build();
        service.save(course);
        mockMvc.perform
                (MockMvcRequestBuilders.delete(ENTITY_PATH, course.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}