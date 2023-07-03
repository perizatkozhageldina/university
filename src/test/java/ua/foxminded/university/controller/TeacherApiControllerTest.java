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
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.service.TeacherService;

import java.util.Arrays;

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
    private ObjectMapper objectMapper;

    @MockBean
    private TeacherService service;

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

        Mockito.when(service.getAll()).thenReturn(Arrays.asList(teacher1, teacher2, teacher3));

        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(teacher1, teacher2, teacher3))));
    }

    @Test
    public void shouldReturnOneJsonElement_whenViewOneMethodExecuted() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(teacher);
        mockMvc.perform(
                MockMvcRequestBuilders.get(ENTITY_PATH, teacher.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void shouldAddRecord_whenAddMethodCalled() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(teacher);
        mockMvc.perform(
                MockMvcRequestBuilders.post(INDEX_PATH)
                        .content(objectMapper.writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(teacher)));
    }

    @Test
    public void shouldUpdateRecord_whenUpdateMethodCalled() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        TeacherDTO updatedTeacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(12).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(teacher);
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(teacher);

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
    public void shouldDeleteEntity_whenDeleteMethodCalled() throws Exception {
        TeacherDTO teacher = TeacherDTO.builder().id(1L).name(TEACHER_NAME).surname(TEACHER_SURNAME).hours(1).build();
        Mockito.when(service.getById(Mockito.anyLong())).thenReturn(teacher);
        mockMvc.perform
                (MockMvcRequestBuilders.delete(ENTITY_PATH, teacher.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}