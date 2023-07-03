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
import ua.foxminded.university.dto.GroupDTO;
import ua.foxminded.university.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@Tag(name = "Group Controller", description = "API endpoints for managing groups")
public class GroupApiController {
    private GroupService service;

    @Autowired
    public GroupApiController(GroupService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all groups")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = GroupDTO.class))))
    public List<GroupDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get group by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = GroupDTO.class))),
            @ApiResponse(responseCode = "404", description = "Group not found")})
    public ResponseEntity<GroupDTO> getById(@PathVariable long id) {
        GroupDTO groupDTO = service.getById(id);
        if (groupDTO != null) {
            return ResponseEntity.ok(groupDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new group")
    @ApiResponse(responseCode = "201", description = "Group created", content = @Content(schema = @Schema(implementation = GroupDTO.class)))
    public ResponseEntity<GroupDTO> add(@RequestBody GroupDTO groupDTO) {
        GroupDTO savedGroupDTO = service.save(groupDTO);
        if (savedGroupDTO != null) {
            return ResponseEntity.ok(savedGroupDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = GroupDTO.class))),
            @ApiResponse(responseCode = "404", description = "Group not found")})
    public ResponseEntity<GroupDTO> update(@PathVariable Long id, @RequestBody GroupDTO updatedGroupDTO) {
        GroupDTO groupDTO = service.getById(id);
        if (groupDTO != null) {
            groupDTO.setName(updatedGroupDTO.getName());
            groupDTO.setMaxStudents(updatedGroupDTO.getMaxStudents());
            GroupDTO savedGroupDTO = service.save(groupDTO);
            return ResponseEntity.ok(savedGroupDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted a group"),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Group not found")})
    public ResponseEntity<GroupDTO> delete(@PathVariable("id") Long id) {
        GroupDTO groupDTO = service.getById(id);
        if (groupDTO != null) {
            service.deleteById(id);
            return ResponseEntity.ok(groupDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
//