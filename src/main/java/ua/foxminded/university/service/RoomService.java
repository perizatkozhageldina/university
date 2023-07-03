package ua.foxminded.university.service;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.repository.RoomJdbcRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class RoomService {
    private RoomJdbcRepository dao;
    private ModelMapper modelMapper;

    @Autowired
    public RoomService(RoomJdbcRepository dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public RoomDTO save(@Valid RoomDTO roomDTO) throws ServiceException {
        Room room = modelMapper.map(roomDTO, Room.class);
        return modelMapper.map(dao.save(room), RoomDTO.class);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public RoomDTO getById(long id) throws ServiceException {
        Room room = dao.findById(id).orElseThrow(() -> new ServiceException("Room not found with ID:" + id));
        return modelMapper.map(room, RoomDTO.class);
    }

    public List<RoomDTO> getAll() throws ServiceException {
        return dao.findAll().stream().map(room -> modelMapper.map(room, RoomDTO.class)).collect(Collectors.toList());
    }
}//