package ua.foxminded.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.service.LectureService;

import java.util.List;

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
    private LectureService lectureService;

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

        lectureService.save(lecture1);
        lectureService.save(lecture2);
        lectureService.save(lecture3);

        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(LECTURE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].number", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(LECTURE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].number", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is(LECTURE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].number", Matchers.is(3)));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        LectureDTO lecture1 = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        LectureDTO lecture2 = LectureDTO.builder().id(2L).name(LECTURE_NAME).number(2).build();
        LectureDTO lecture3 = LectureDTO.builder().id(3L).name(LECTURE_NAME).number(3).build();

        lectureService.save(lecture1);
        lectureService.save(lecture2);
        lectureService.save(lecture3);

        mockMvc.perform(MockMvcRequestBuilders.get(ENTITY_PATH, lecture1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'id': 1}"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();

        mockMvc.perform(MockMvcRequestBuilders.post(INDEX_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(lecture)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(LECTURE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", Matchers.is(1)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        LectureDTO savedLecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        lectureService.save(savedLecture);

        LectureDTO updatedLecture = LectureDTO.builder().id(1L).name("Updated Lecture").number(1).build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, savedLecture.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedLecture)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated Lecture")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", Matchers.is(1)));
    }

    @Test
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        lectureService.save(lecture);
        mockMvc.perform(MockMvcRequestBuilders.delete(ENTITY_PATH, lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
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
