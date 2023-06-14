package ua.foxminded.university.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.service.RoomService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomApiController {
    private RoomService service;
    private ModelMapper modelMapper;

    @Autowired
    public RoomApiController(RoomService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<RoomDTO> getAll() {
        List<Room> rooms = service.getAll();
        return rooms.stream().map(room -> modelMapper.map(room, RoomDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getById(@PathVariable long id) {
        Room room = service.getById(id);
        if (room != null) {
            RoomDTO roomDTO = modelMapper.map(room, RoomDTO.class);
            return ResponseEntity.ok(roomDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RoomDTO> add(@RequestBody RoomDTO roomDTO) {
        Room room = modelMapper.map(roomDTO, Room.class);
        Room savedRoom = service.save(room);
        if (savedRoom != null) {
            RoomDTO savedRoomDTO = modelMapper.map(savedRoom, RoomDTO.class);
            return ResponseEntity.ok(savedRoomDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @RequestBody RoomDTO updatedRoomDTO) {
        Room updatedRoom = modelMapper.map(updatedRoomDTO, Room.class);
        Room room = service.getById(updatedRoom.getId());
        if (room != null) {
            room.setName(updatedRoom.getName());
            room.setCapacity(updatedRoom.getCapacity());
            Room savedRoom = service.save(room);
            RoomDTO savedRoomDTO = modelMapper.map(savedRoom, RoomDTO.class);
            return ResponseEntity.ok(savedRoomDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Room room = service.getById(id);
        if (room != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
