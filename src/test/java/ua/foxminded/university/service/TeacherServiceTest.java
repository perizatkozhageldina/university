package ua.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.repository.TeacherJdbcRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TeacherServiceTest {
    private TeacherService service;
    @Mock
    private Teacher teacher = Mockito.mock(Teacher.class);
    @Mock
    private TeacherJdbcRepository dao = Mockito.mock(TeacherJdbcRepository.class);

    @BeforeEach
    void init() {
        service = new TeacherService(dao);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        service.save(teacher);
        verify(dao, times(1)).save(teacher);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(dao, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetEntity_whenServiceGetByIdMethodCalled() {
        Mockito.when(dao.findById(anyLong())).thenReturn(Optional.of(new Teacher()));
        service.getById(anyLong());
        Mockito.verify(dao, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(dao, times(1)).deleteById(anyLong());
    }
}
