package ua.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.university.dto.LectureDTO;
import ua.foxminded.university.service.LectureService;

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LectureControllerIntegrationTest {
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

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LectureService service;

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnIndexView_whenListMethodExecuted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(INDEX_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnAddView_whenAddMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        service.save(lecture);
        mockMvc.perform(MockMvcRequestBuilders.get(ADD_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW))
                .andExpect(MockMvcResultMatchers.model().attributeExists(LECTURE_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnSaveView_whenSaveMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        String encoded = URLEncoder.encode(lecture.toString(), "UTF-8");
        mockMvc.perform(MockMvcRequestBuilders.post(SAVE_PATH).param(LECTURE_PARAM, encoded))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(ADD_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnUpdateView_whenUpdateMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        service.save(lecture);
        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_PATH)
                .param("name", "Updated Lecture")
                .param("number", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnEditView_whenEditMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        service.save(lecture);
        mockMvc.perform(MockMvcRequestBuilders.get(EDIT_PATH, lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(EDIT_VIEW))
                .andExpect(model().attributeExists(LECTURE_ATTRIBUTE));
    }

    @Test
    @Sql("classpath:testSchema.sql")
    void shouldReturnIndexView_whenDeleteMethodExecuted() throws Exception {
        LectureDTO lecture = LectureDTO.builder().id(1L).name(LECTURE_NAME).number(1).build();
        service.save(lecture);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW));
    }
}
//