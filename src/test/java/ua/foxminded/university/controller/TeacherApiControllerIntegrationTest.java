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
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.service.TeacherService;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TeacherApiControllerIntegrationTest {
    private static final String TEACHER_NAME = "Name";
    private static final String TEACHER_SURNAME = "Surname";
    private static final String INDEX_PATH = "/api/teachers";
    private static final String ENTITY_PATH = "/api/teachers/{id}";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        TeacherDTO teacher1 = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        TeacherDTO teacher2 = TeacherDTO.builder().id(2L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(2).build();
        TeacherDTO teacher3 = TeacherDTO.builder().id(3L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(3).build();
        service.save(teacher1);
        service.save(teacher2);
        service.save(teacher3);
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(teacher1, teacher2, teacher3))));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        service.save(teacher);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, teacher.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        service.save(teacher);
        mockMvc.perform(
                MockMvcRequestBuilders.post(INDEX_PATH)
                        .content(objectMapper.writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(teacher)));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        TeacherDTO updatedTeacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(12).build();
        service.save(teacher);

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, teacher.getId())
                .content(objectMapper.writeValueAsString(updatedTeacher))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(TEACHER_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", Matchers.is(TEACHER_SURNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hours", Matchers.is(12)));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        service.save(teacher);
        mockMvc.perform
                (MockMvcRequestBuilders.delete(ENTITY_PATH, teacher.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}