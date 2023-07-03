package ua.foxminded.university.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
class LectureControllerTest {
    private static final String INDEX_PATH = "/lectures";
    private static final String ADD_PATH = "/lectures/add";
    private static final String SAVE_PATH = "/lectures/save";
    private static final String UPDATE_PATH = "/lectures/update";
    private static final String EDIT_PATH = "/lectures/{id}/edit";
    private static final String DELETE_PATH = "/lectures/{id}/delete";
    private static final String INDEX_VIEW = "lecture/index";
    private static final String ADD_VIEW = "lecture/add";
    private static final String EDIT_VIEW = "lecture/edit";
    private static final String REDIRECT_VIEW = "redirect:/lectures";
    private static final String INDEX_ATTRIBUTE = "lectures";
    private static final String LECTURE_ATTRIBUTE = "lecture";
    private static final String LECTURE_PARAM = "Lecture";
    private static final String LECTURE_NAME = "Lecture";

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private LectureService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .dispatchOptions(true).build();
    }

    @Test
    void shouldReturnIndexView_whenListMethodExecuted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(INDEX_ATTRIBUTE));
    }

    @Test
    void shouldReturnAddView_whenAddMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(lecture);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(LECTURE_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        String encoded = URLEncoder.encode(lecture.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(LECTURE_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(lecture);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Lecture")
                .param("number", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        Mockito.when(service.getById(lecture.getId())).thenReturn(lecture);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(LECTURE_ATTRIBUTE));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        Mockito.when(service.save(Mockito.any())).thenReturn(lecture);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }
}
//