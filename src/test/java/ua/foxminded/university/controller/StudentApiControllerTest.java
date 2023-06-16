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
import ua.foxminded.university.dto.StudentDTO;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.StudentService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class StudentApiControllerTest {
    private static final String STUDENT_NAME = "Name";
    private static final String STUDENT_SURNAME = "Surname";
    private static final String INDEX_PATH = "/api/students";
    private static final String ENTITY_PATH = "/api/students/{id}";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private StudentService studentService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .dispatchOptions(true).build();
    }

    @Test
    public void shouldReturnAllRecords_whenViewAllMethodExecuted() throws Exception {
        StudentDTO student1 = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        StudentDTO student2 = StudentDTO.builder().id(2L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(2).build();
        StudentDTO student3 = StudentDTO.builder().id(3L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(3).build();

        studentService.save(student1);
        studentService.save(student2);
        studentService.save(student3);

        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(STUDENT_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].surname", Matchers.is(STUDENT_SURNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].academicYear", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(STUDENT_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].surname", Matchers.is(STUDENT_SURNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].academicYear", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is(STUDENT_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].surname", Matchers.is(STUDENT_SURNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].academicYear", Matchers.is(3)));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        StudentDTO student1 = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        StudentDTO student2 = StudentDTO.builder().id(2L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(2).build();
        StudentDTO student3 = StudentDTO.builder().id(3L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(3).build();

        studentService.save(student1);
        studentService.save(student2);
        studentService.save(student3);

        mockMvc.perform(MockMvcRequestBuilders.get(ENTITY_PATH, student1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'id': 1}"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();

        mockMvc.perform(MockMvcRequestBuilders.post(INDEX_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(student)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(STUDENT_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", Matchers.is(STUDENT_SURNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.academicYear", Matchers.is(1)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        StudentDTO savedStudent = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        studentService.save(savedStudent);

        StudentDTO updatedStudent = StudentDTO.builder().id(1L).name("Updated Name").surname("Updated Surname").academicYear(1).build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, savedStudent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedStudent)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated Name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", Matchers.is("Updated Surname")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.academicYear", Matchers.is(1)));
    }

    @Test
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        studentService.save(student);
        mockMvc.perform(MockMvcRequestBuilders.delete(ENTITY_PATH, student.getId()))
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