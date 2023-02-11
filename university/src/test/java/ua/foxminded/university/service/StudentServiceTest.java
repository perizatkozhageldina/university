package ua.foxminded.university.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ua.foxminded.university.dao.StudentJdbcDAO;
import ua.foxminded.university.model.Student;

class StudentServiceTest {
    private StudentService service;
    @Mock 
    Student student = Mockito.mock(Student.class);    
    @Mock
    StudentJdbcDAO dao = Mockito.mock(StudentJdbcDAO.class);    
    
    @BeforeEach
    void init() {
        service = new StudentService(dao);
    }    

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        service.add(student);
        verify(dao, times(1)).add(student);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(dao, times(1)).getAll();
    }
    
    @Test
    void shouldCallDaoGetCourse_whenServiceGetByIdMethodCalled() {
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyLong());
    }
    
    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(dao, times(1)).deleteById(anyLong());
    }
}
