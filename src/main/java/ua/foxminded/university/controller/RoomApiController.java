package ua.foxminded.university.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.service.RoomService;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomApiController {
    private RoomService service;

    @Autowired
    public RoomApiController(RoomService service) {
        this.service = service;
    }

    @GetMapping
    public List<RoomDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getById(@PathVariable long id) {
        RoomDTO roomDTO = service.getById(id);
        if (roomDTO != null) {
            return ResponseEntity.ok(roomDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RoomDTO> add(@RequestBody RoomDTO roomDTO) {
        RoomDTO savedRoomDTO = service.save(roomDTO);
        if (savedRoomDTO != null) {
            return ResponseEntity.ok(savedRoomDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @RequestBody RoomDTO updatedRoomDTO) {
        RoomDTO roomDTO = service.getById(id);
        if (roomDTO != null) {
            roomDTO.setName(updatedRoomDTO.getName());
            roomDTO.setCapacity(updatedRoomDTO.getCapacity());
            RoomDTO savedRoomDTO = service.save(roomDTO);
            return ResponseEntity.ok(savedRoomDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        RoomDTO roomDTO = service.getById(id);
        if (roomDTO != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
