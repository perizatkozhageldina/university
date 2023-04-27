package ua.foxminded.university.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.mock.mockito.MockBean;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.repository.CourseJdbcRepository;

import java.util.Optional;

class CourseServiceTest {
    private CourseService service;
    @Mock
    private Course course = Mockito.mock(Course.class);
    @MockBean
    private CourseJdbcRepository dao = Mockito.mock(CourseJdbcRepository.class);

    @BeforeEach
    void init() {
        service = new CourseService(dao);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        service.save(course);
        verify(dao, times(1)).save(course);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(dao, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetCourse_whenServiceGetByIdMethodCalled() {
        Course course = Course.builder().id(5).hours(5).level(5).build();
//        Mockito.when(dao.findById(5L)).thenReturn(Optional.of(course));
        service.getById(course.getId());
        verify(dao, times(1)).findById(5L);
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(dao, times(1)).deleteById(anyLong());
    }
}