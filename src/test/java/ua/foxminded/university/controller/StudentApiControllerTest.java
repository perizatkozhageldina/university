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
import ua.foxminded.university.dto.StudentDTO;
import ua.foxminded.university.service.StudentService;

import java.util.Arrays;

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
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService service;

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

        Mockito.when(service.getAll()).thenReturn(Arrays.asList(student1, student2, student3));

        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(student1, student2, student3))));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(student);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, student.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(student);
        mockMvc.perform(
                MockMvcRequestBuilders.post(INDEX_PATH)
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(student)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        StudentDTO updatedStudent = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(5).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(student);
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.put(ENTITY_PATH, student.getId())
                .content(objectMapper.writeValueAsString(updatedStudent))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(STUDENT_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", Matchers.is(STUDENT_SURNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.academicYear", Matchers.is(5)));
    }

    @Test
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        StudentDTO student = StudentDTO.builder().id(1L).name(STUDENT_NAME).surname(STUDENT_SURNAME).academicYear(1).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(student);
        mockMvc.perform
                (MockMvcRequestBuilders.delete(ENTITY_PATH, student.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}