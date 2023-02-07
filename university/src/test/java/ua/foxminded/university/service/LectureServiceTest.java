package ua.foxminded.university.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import ua.foxminded.university.dao.LectureJdbcDAO;
import ua.foxminded.university.model.Lecture;

class LectureServiceTest {
    @Mock 
    Lecture lecture = Mockito.mock(Lecture.class);    
    @Mock
    LectureJdbcDAO dao = Mockito.mock(LectureJdbcDAO.class);    
    LectureService service = new LectureService();
    
    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(service, "dao", dao);
    }    

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        service.add(lecture);
        verify(dao, times(1)).add(lecture);
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
