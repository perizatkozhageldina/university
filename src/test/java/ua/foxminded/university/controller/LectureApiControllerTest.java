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
import ua.foxminded.university.dto.LectureDTO;
import ua.foxminded.university.service.LectureService;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class LectureApiControllerTest {
    private static final String LECTURE_NAME = "Lecture";
    private static final String INDEX_PATH = "/api/lectures";
    private static final String ENTITY_PATH = "/api/lectures/{id}";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LectureService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .dispatchOptions(true).build();
    }

    @Test
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        LectureDTO lecture1 = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        LectureDTO lecture2 = LectureDTO.builder().id(2L).name(LECTURE_NAME).number(2).build();
        LectureDTO lecture3 = LectureDTO.builder().id(3L).name(LECTURE_NAME).number(3).build();
        Mockito.when(service.getAll()).thenReturn(Arrays.asList(lecture1, lecture2, lecture3));
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(lecture1, lecture2, lecture3))));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(lecture);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(lecture);
        mockMvc.perform(
                MockMvcRequestBuilders.post(INDEX_PATH)
                        .content(objectMapper.writeValueAsString(lecture))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(lecture)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        LectureDTO updatedLecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(10).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(lecture);
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(lecture);

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, lecture.getId())
                .content(objectMapper.writeValueAsString(updatedLecture))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(LECTURE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", Matchers.is(10)));
    }

    @Test
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(lecture);
        mockMvc.perform
                (MockMvcRequestBuilders.delete(ENTITY_PATH, lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}