package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public List<Room> viewAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Room> viewOne(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Room> add(@RequestBody Room room) {
        service.save(room);
        Optional<Room> savedRoom = service.getById(room.getId());
        return savedRoom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody Room updatedRoom) {
        Optional<Room> optionalRoom = service.getById(updatedRoom.getId());
        if (optionalRoom.isPresent()) {
            Room existingRoom  = optionalRoom.get();
            existingRoom.setName(updatedRoom.getName());
            existingRoom.setCapacity(updatedRoom.getCapacity());
            Room savedRoom = service.save(existingRoom);
            return ResponseEntity.ok(savedRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional<Room> roomOptional = service.getById(id);
        if (roomOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
