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
import ua.foxminded.university.model.Course;
import ua.foxminded.university.repository.CourseJdbcRepository;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    private CourseService service;

    @Mock
    private CourseJdbcRepository daoMock;

    @Mock
    private ModelMapper modelMapper;

    CourseDTO courseDTO = new CourseDTO();
    Course course = new Course();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new CourseService(daoMock, modelMapper);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        Mockito.when(modelMapper.map(courseDTO, Course.class)).thenReturn(course);
        Mockito.when(daoMock.save(Mockito.any(Course.class))).thenReturn(course);
        service.save(courseDTO);
        verify(modelMapper, times(1)).map(courseDTO, Course.class);
        verify(daoMock, times(1)).save(course);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(daoMock, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetEntity_whenServiceGetByIdMethodCalled() {
        Course course = Course.builder().id(1L).name("").level(1).hours(10).build();
        Mockito.when(daoMock.findById(course.getId())).thenReturn(java.util.Optional.of(course));
        service.getById(course.getId());
        verify(daoMock, times(1)).findById(course.getId());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(daoMock, times(1)).deleteById(anyLong());
    }
}