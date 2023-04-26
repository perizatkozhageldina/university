package ua.foxminded.university.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ua.foxminded.university.model.Room;
import ua.foxminded.university.repository.RepositoryException;
import ua.foxminded.university.repository.RoomJdbcRepository;

class RoomServiceTest {
    private RoomService service;
    @Mock
    private Room room = Mockito.mock(Room.class);
    @Mock
    private RoomJdbcRepository dao = Mockito.mock(RoomJdbcRepository.class);

    @BeforeEach
    void init() {
        service = new RoomService(dao);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() throws RepositoryException {
        service.add(room);
        verify(dao, times(1)).add(room);
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