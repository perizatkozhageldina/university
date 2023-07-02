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
import ua.foxminded.university.dto.LectureDTO;
import ua.foxminded.university.service.LectureService;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LectureApiControllerIntegrationTest {
    private static final String LECTURE_NAME = "Lecture";
    private static final String INDEX_PATH = "/api/lectures";
    private static final String ENTITY_PATH = "/api/lectures/{id}";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LectureService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        LectureDTO lecture1 = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        LectureDTO lecture2 = LectureDTO.builder().id(2L).name(LECTURE_NAME).number(2).build();
        LectureDTO lecture3 = LectureDTO.builder().id(3L).name(LECTURE_NAME).number(3).build();
        service.save(lecture1);
        service.save(lecture2);
        service.save(lecture3);
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(lecture1, lecture2, lecture3))));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        service.save(lecture);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        service.save(lecture);
        mockMvc.perform(
                MockMvcRequestBuilders.post(INDEX_PATH)
                        .content(objectMapper.writeValueAsString(lecture))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(lecture)));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        LectureDTO updatedLecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(10).build();
        service.save(lecture);

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, lecture.getId())
                .content(objectMapper.writeValueAsString(updatedLecture))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(LECTURE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", Matchers.is(10)));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        service.save(lecture);
        mockMvc.perform
                (MockMvcRequestBuilders.delete(ENTITY_PATH, lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
