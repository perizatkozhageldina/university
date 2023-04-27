package ua.foxminded.university.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.repository.LectureJdbcRepository;

class LectureServiceTest {
    private LectureService service;
    @Mock
    private Lecture lecture = Mockito.mock(Lecture.class);
    @Mock
    private LectureJdbcRepository dao = Mockito.mock(LectureJdbcRepository.class);

    @BeforeEach
    void init() {
        service = new LectureService(dao);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        service.save(lecture);
        verify(dao, times(1)).save(lecture);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(dao, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetCourse_whenServiceGetByIdMethodCalled() {
        service.getById(anyInt());
        verify(dao, times(1)).findById(anyLong());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(dao, times(1)).deleteById(anyLong());
    }
}
