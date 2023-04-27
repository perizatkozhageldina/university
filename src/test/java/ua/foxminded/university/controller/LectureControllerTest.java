package ua.foxminded.university.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.foxminded.university.config.AppConfig;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.service.LectureService;

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

    private MockMvc mockMvc;

    @Mock
    private LectureService mockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new LectureController(mockService)).build();
    }

    @Test
    void shouldReturnIndexView_whenListMethodExecuted() throws Exception {
        List<Lecture> lectureList = Arrays.asList(Lecture.builder().id(1L).number(1).build(),
                Lecture.builder().id(2L).number(2).build(), Lecture.builder().id(3L).number(3).build());

        Mockito.when(mockService.getAll()).thenReturn(lectureList);

        mockMvc.perform(get(INDEX_PATH)).andExpect(status().isOk()).andExpect(view().name(INDEX_VIEW))
                .andExpect(model().attributeExists(INDEX_ATTRIBUTE));
    }

    @Test
    void shouldReturnAddView_whenAddMethodExecuted() throws Exception {
        Lecture lecture = Lecture.builder().id(1L).number(1).build();

        mockMvc.perform(get(ADD_PATH)).andExpect(status().isOk()).andExpect(view().name(ADD_VIEW))
                .andExpect(model().attributeExists(LECTURE_ATTRIBUTE));
    }

    @Test
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        Lecture lecture = Lecture.builder().id(1L).number(1).build();
        String encoded = URLEncoder.encode(lecture.toString(), "UTF-8");

        mockMvc.perform(post(SAVE_PATH).param(LECTURE_PARAM, encoded)).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        Lecture lecture = Lecture.builder().id(1L).number(1).build();
        String encoded = URLEncoder.encode(lecture.toString(), "UTF-8");

        mockMvc.perform(patch(UPDATE_PATH).param(LECTURE_PARAM, encoded)).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }

    @Test
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        Lecture lecture = Lecture.builder().id(1L).number(1).build();

        Mockito.when(mockService.getById(lecture.getId())).thenReturn(lecture);

        mockMvc.perform(get(EDIT_PATH, "1")).andExpect(status().isOk()).andExpect(view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(LECTURE_ATTRIBUTE));
    }

    @Test
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        Lecture lecture = Lecture.builder().id(1L).number(1).build();

        mockMvc.perform(delete(DELETE_PATH, "1")).andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW));
    }
}
