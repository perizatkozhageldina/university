package ua.foxminded.university.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.foxminded.university.model.Course;
import ua.foxminded.university.service.CourseService;

class CourseControllerTest {

    private MockMvc mockMvc;
    
    @Mock
    private CourseService service;
    
    @InjectMocks
    private CourseController courseController;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc =  MockMvcBuilders.standaloneSetup(courseController).build();
    }
    
    @Test
    void testGetUserById() throws Exception {
        Course course = Course.builder().id(1).level(1).hours(24).build();

        // Mock the service call
        Mockito.when(service.getById(1)).thenReturn(course);

        // Perform the request and assert the response
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk());
    }
}
