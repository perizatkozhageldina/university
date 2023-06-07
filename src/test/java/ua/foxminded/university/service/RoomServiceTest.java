package ua.foxminded.university.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ua.foxminded.university.model.Course;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.repository.RoomJdbcRepository;

import java.util.Optional;

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
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        service.save(room);
        verify(dao, times(1)).save(room);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(dao, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetEntity_whenServiceGetByIdMethodCalled() {
        Mockito.when(dao.findById(anyLong())).thenReturn(Optional.of(new Room()));
        service.getById(anyLong());
        Mockito.verify(dao, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(dao, times(1)).deleteById(anyLong());
    }
}
