package ua.foxminded.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ua.foxminded.university.dto.GroupDTO;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.repository.CourseJdbcRepository;
import ua.foxminded.university.repository.GroupJdbcRepository;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    private GroupService service;

    @Mock
    private GroupJdbcRepository daoMock;

    @Mock
    private ModelMapper modelMapper;

    GroupDTO groupDTO = new GroupDTO();
    Group group = new Group();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new GroupService(daoMock, modelMapper);
    }

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {
        Mockito.when(modelMapper.map(groupDTO, Group.class)).thenReturn(group);
        Mockito.when(daoMock.save(Mockito.any(Group.class))).thenReturn(group);
        service.save(groupDTO);
        verify(modelMapper, times(1)).map(groupDTO, Group.class);
        verify(daoMock, times(1)).save(group);
    }

    @Test
    void shouldCallDaoGetAll_whenServiceGetAllMethodCalled() {
        service.getAll();
        verify(daoMock, times(1)).findAll();
    }

    @Test
    void shouldCallDaoGetEntity_whenServiceGetByIdMethodCalled() {
        Group group = Group.builder().id(1L).name("").maxStudents(10).build();
        Mockito.when(daoMock.findById(group.getId())).thenReturn(java.util.Optional.of(group));
        service.getById(group.getId());
        verify(daoMock, times(1)).findById(group.getId());
    }

    @Test
    void shouldCallDaoDelete_whenServiceDeleteMethodCalled() {
        service.deleteById(anyLong());
        verify(daoMock, times(1)).deleteById(anyLong());
    }
}
//