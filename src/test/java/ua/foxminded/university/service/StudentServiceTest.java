package ua.foxminded.university.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ua.foxminded.university.model.Student;
import ua.foxminded.university.repository.StudentJdbcRepository;

class StudentServiceTest {
    private StudentService service;
    @Mock
    private Student student = Mockito.mock(Student.class);
    @Mock
    private StudentJdbcRepository dao = Mockito.mock(StudentJdbcRepository.class);

    @BeforeEach
    void init() {
        service = new StudentService(dao);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        service.save(student);
        verify(dao, times(1)).save(student);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(dao, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetEntity_whenServiceGetByIdMethodCalled() {
        service.getById(anyInt());
        verify(dao, times(1)).findById(anyLong());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(dao, times(1)).deleteById(anyLong());
    }
}
