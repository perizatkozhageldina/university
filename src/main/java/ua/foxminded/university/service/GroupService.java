package ua.foxminded.university.service;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.dto.GroupDTO;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.repository.GroupJdbcRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class GroupService {
    private GroupJdbcRepository dao;
    private ModelMapper modelMapper;

    @Autowired
    public GroupService(GroupJdbcRepository dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public GroupDTO save(@Valid GroupDTO groupDTO) throws ServiceException {
        Group group = modelMapper.map(groupDTO, Group.class);
        return modelMapper.map(dao.save(group), GroupDTO.class);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public GroupDTO getById(long id) throws ServiceException {
        Group group = dao.findById(id).orElseThrow(() -> new ServiceException("Group not found with ID:" + id));
        return modelMapper.map(group, GroupDTO.class);
    }

    public List<GroupDTO> getAll() throws ServiceException {
        return dao.findAll().stream().map(group -> modelMapper.map(group, GroupDTO.class)).collect(Collectors.toList());
    }
}//