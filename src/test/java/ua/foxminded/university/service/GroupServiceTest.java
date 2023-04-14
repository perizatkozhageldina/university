package ua.foxminded.university.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ua.foxminded.university.model.Group;
import ua.foxminded.university.repository.RepositoryException;
import ua.foxminded.university.repository.GroupJdbcRepository;

class GroupServiceTest {
    private GroupService service;
    @Mock
    private Group group = Mockito.mock(Group.class);
    @Mock
    private GroupJdbcRepository dao = Mockito.mock(GroupJdbcRepository.class);

    @BeforeEach
    void init() {
        service = new GroupService(dao);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() throws RepositoryException {
        service.add(group);
        verify(dao, times(1)).add(group);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() throws RepositoryException {
        service.getAll();
        verify(dao, times(1)).getAll();
    }

    @Test
    void shouldCallDaoGetCourse_whenServiceGetByIdMethodCalled() throws RepositoryException {
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyLong());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() throws RepositoryException {
        service.deleteById(anyLong());
        verify(dao, times(1)).deleteById(anyLong());
    }
}
