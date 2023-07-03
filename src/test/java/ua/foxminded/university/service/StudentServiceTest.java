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
import ua.foxminded.university.dto.StudentDTO;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.repository.CourseJdbcRepository;
import ua.foxminded.university.repository.StudentJdbcRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    private StudentService service;

    @Mock
    private StudentJdbcRepository daoMock;

    @Mock
    private ModelMapper modelMapper;

    StudentDTO studentDTO = new StudentDTO();
    Student student = new Student();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new StudentService(daoMock, modelMapper);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        Mockito.when(modelMapper.map(studentDTO, Student.class)).thenReturn(student);
        Mockito.when(daoMock.save(Mockito.any(Student.class))).thenReturn(student);
        service.save(studentDTO);
        verify(modelMapper, times(1)).map(studentDTO, Student.class);
        verify(daoMock, times(1)).save(student);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(daoMock, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetEntity_whenServiceGetByIdMethodCalled() {
        Student student = Student.builder().id(1L).name("").surname("").academicYear(1).groupId(1L).build();
        Mockito.when(daoMock.findById(student.getId())).thenReturn(java.util.Optional.of(student));
        service.getById(student.getId());
        verify(daoMock, times(1)).findById(student.getId());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(daoMock, times(1)).deleteById(anyLong());
    }
}