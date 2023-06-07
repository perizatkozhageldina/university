package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.service.RoomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomApiController {
    private RoomService service;

    @Autowired
    public RoomApiController(RoomService service) {
        this.service = service;
    }

    @GetMapping
    public List<Room> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable long id) {
        Room room = service.getById(id);
        if (room != null) {
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Room> add(@RequestBody Room room) {
        Room savedRoom = service.save(room);
        if (savedRoom != null) {
            return ResponseEntity.ok(savedRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody Room updatedRoom) {
        Room room = service.getById(updatedRoom.getId());
        if (room != null) {
            room.setName(updatedRoom.getName());
            room.setCapacity(updatedRoom.getCapacity());
            Room savedRoom = service.save(room);
            return ResponseEntity.ok(savedRoom);
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
