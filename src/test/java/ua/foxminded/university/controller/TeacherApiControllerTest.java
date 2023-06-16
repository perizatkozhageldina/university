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
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.TeacherService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class TeacherApiControllerTest {
    private static final String TEACHER_NAME = "Name";
    private static final String TEACHER_SURNAME = "Surname";
    private static final String INDEX_PATH = "/api/teachers";
    private static final String ENTITY_PATH = "/api/teachers/{id}";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TeacherService teacherService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .dispatchOptions(true).build();
    }

    @Test
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        TeacherDTO teacher1 = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        TeacherDTO teacher2 = TeacherDTO.builder().id(2L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(2).build();
        TeacherDTO teacher3 = TeacherDTO.builder().id(3L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(3).build();

        teacherService.save(teacher1);
        teacherService.save(teacher2);
        teacherService.save(teacher3);

        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(TEACHER_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].surname", Matchers.is(TEACHER_SURNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].hours", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(TEACHER_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].surname", Matchers.is(TEACHER_SURNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].hours", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is(TEACHER_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].surname", Matchers.is(TEACHER_SURNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].hours", Matchers.is(3)));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        TeacherDTO teacher1 = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        TeacherDTO teacher2 = TeacherDTO.builder().id(2L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(2).build();
        TeacherDTO teacher3 = TeacherDTO.builder().id(3L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(3).build();

        teacherService.save(teacher1);
        teacherService.save(teacher2);
        teacherService.save(teacher3);

        mockMvc.perform(MockMvcRequestBuilders.get(ENTITY_PATH, teacher1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'id': 1}"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();

        mockMvc.perform(MockMvcRequestBuilders.post(INDEX_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(teacher)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(TEACHER_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", Matchers.is(TEACHER_SURNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hours", Matchers.is(1)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        TeacherDTO savedTeacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        teacherService.save(savedTeacher);

        TeacherDTO updatedTeacher = TeacherDTO.builder().id(1L).name("Updated Name").surname("Updated Surname").hours(1).build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, savedTeacher.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedTeacher)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated Name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", Matchers.is("Updated Surname")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hours", Matchers.is(1)));
    }

    @Test
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        teacherService.save(teacher);
        mockMvc.perform(MockMvcRequestBuilders.delete(ENTITY_PATH, teacher.getId()))
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
