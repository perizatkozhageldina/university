package ua.foxminded.university.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ua.foxminded.university.dao.DAOException;
import ua.foxminded.university.dao.LectureJdbcDAO;
import ua.foxminded.university.model.Lecture;

class LectureServiceTest {
    private LectureService service;
    @Mock 
    Lecture lecture = Mockito.mock(Lecture.class);    
    @Mock
    LectureJdbcDAO dao = Mockito.mock(LectureJdbcDAO.class);        
    
    @BeforeEach
    void init() {
        service = new LectureService(dao);
    }    

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() throws DAOException {
        service.add(lecture);
        verify(dao, times(1)).add(lecture);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() throws DAOException {
        service.getAll();
        verify(dao, times(1)).getAll();
    }
    
    @Test
    void shouldCallDaoGetCourse_whenServiceGetByIdMethodCalled() throws DAOException {
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyLong());
    }
    
    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() throws DAOException {
        service.deleteById(anyLong());
        verify(dao, times(1)).deleteById(anyLong());
    }
}
