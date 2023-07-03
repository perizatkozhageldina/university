package ua.foxminded.university.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.repository.CourseJdbcRepository;
import ua.foxminded.university.repository.RoomJdbcRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    private RoomService service;

    @Mock
    private RoomJdbcRepository daoMock;

    @Mock
    private ModelMapper modelMapper;

    RoomDTO roomDTO = new RoomDTO();
    Room room = new Room();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new RoomService(daoMock, modelMapper);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        Mockito.when(modelMapper.map(roomDTO, Room.class)).thenReturn(room);
        Mockito.when(daoMock.save(Mockito.any(Room.class))).thenReturn(room);
        service.save(roomDTO);
        verify(modelMapper, times(1)).map(roomDTO, Room.class);
        verify(daoMock, times(1)).save(room);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(daoMock, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetEntity_whenServiceGetByIdMethodCalled() {
        Room room = Room.builder().id(1L).name("").capacity(10).build();
        Mockito.when(daoMock.findById(room.getId())).thenReturn(java.util.Optional.of(room));
        service.getById(room.getId());
        verify(daoMock, times(1)).findById(room.getId());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(daoMock, times(1)).deleteById(anyLong());
    }
}
//