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
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.repository.CourseJdbcRepository;
import ua.foxminded.university.repository.TeacherJdbcRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    private TeacherService service;

    @Mock
    private TeacherJdbcRepository daoMock;

    @Mock
    private ModelMapper modelMapper;

    TeacherDTO teacherDTO = new TeacherDTO();
    Teacher teacher = new Teacher();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new TeacherService(daoMock, modelMapper);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        Mockito.when(modelMapper.map(teacherDTO, Teacher.class)).thenReturn(teacher);
        Mockito.when(daoMock.save(Mockito.any(Teacher.class))).thenReturn(teacher);
        service.save(teacherDTO);
        verify(modelMapper, times(1)).map(teacherDTO, Teacher.class);
        verify(daoMock, times(1)).save(teacher);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(daoMock, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetEntity_whenServiceGetByIdMethodCalled() {
        Teacher teacher = Teacher.builder().id(1L).name("").surname("").category("").hours(10).build();
        Mockito.when(daoMock.findById(teacher.getId())).thenReturn(java.util.Optional.of(teacher));
        service.getById(teacher.getId());
        verify(daoMock, times(1)).findById(teacher.getId());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(daoMock, times(1)).deleteById(anyLong());
    }
}
//