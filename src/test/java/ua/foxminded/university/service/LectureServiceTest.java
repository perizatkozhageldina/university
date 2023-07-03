package ua.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.dto.LectureDTO;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.repository.CourseJdbcRepository;
import ua.foxminded.university.repository.LectureJdbcRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {
    private LectureService service;

    @Mock
    private LectureJdbcRepository daoMock;

    @Mock
    private ModelMapper modelMapper;

    LectureDTO lectureDTO = new LectureDTO();
    Lecture lecture = new Lecture();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new LectureService(daoMock, modelMapper);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        Mockito.when(modelMapper.map(lectureDTO, Lecture.class)).thenReturn(lecture);
        Mockito.when(daoMock.save(Mockito.any(Lecture.class))).thenReturn(lecture);
        service.save(lectureDTO);
        verify(modelMapper, times(1)).map(lectureDTO, Lecture.class);
        verify(daoMock, times(1)).save(lecture);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(daoMock, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetEntity_whenServiceGetByIdMethodCalled() {
        Lecture lecture = Lecture.builder().id(1L).name("").number(1).build();
        Mockito.when(daoMock.findById(lecture.getId())).thenReturn(java.util.Optional.of(lecture));
        service.getById(lecture.getId());
        verify(daoMock, times(1)).findById(lecture.getId());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(daoMock, times(1)).deleteById(anyLong());
    }
}