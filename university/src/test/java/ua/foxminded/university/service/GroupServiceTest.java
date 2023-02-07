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

import ua.foxminded.university.dao.GroupJdbcDAO;
import ua.foxminded.university.model.Group;

class GroupServiceTest {
    @Mock 
    Group group = Mockito.mock(Group.class);    
    @Mock
    GroupJdbcDAO dao = Mockito.mock(GroupJdbcDAO.class);    
    GroupService service = new GroupService();
    
    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(service, "dao", dao);
    }    

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        service.add(group);
        verify(dao, times(1)).add(group);
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
