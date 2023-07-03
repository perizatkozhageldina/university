package ua.foxminded.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Room Controller", description = "API endpoints for managing rooms")
public class RoomApiController {
    private RoomService service;

    @Autowired
    public RoomApiController(RoomService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all rooms")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoomDTO.class))))
    public List<RoomDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = RoomDTO.class))),
            @ApiResponse(responseCode = "404", description = "Room not found")})
    public ResponseEntity<RoomDTO> getById(@PathVariable long id) {
        RoomDTO roomDTO = service.getById(id);
        if (roomDTO != null) {
            return ResponseEntity.ok(roomDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new room")
    @ApiResponse(responseCode = "201", description = "Room created", content = @Content(schema = @Schema(implementation = RoomDTO.class)))
    public ResponseEntity<RoomDTO> add(@RequestBody RoomDTO roomDTO) {
        RoomDTO savedRoomDTO = service.save(roomDTO);
        if (savedRoomDTO != null) {
            return ResponseEntity.ok(savedRoomDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = RoomDTO.class))),
            @ApiResponse(responseCode = "404", description = "Room not found")})
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
    @Operation(summary = "Delete a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted a room"),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Room not found")})
    public ResponseEntity<RoomDTO> delete(@PathVariable("id") Long id) {
        RoomDTO roomDTO = service.getById(id);
        if (roomDTO != null) {
            service.deleteById(id);
            return ResponseEntity.ok(roomDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}