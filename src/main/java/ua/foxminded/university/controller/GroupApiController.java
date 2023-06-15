package ua.foxminded.university.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.GroupDTO;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.GroupService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupApiController {
    private GroupService service;

    @Autowired
    public GroupApiController(GroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<GroupDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getById(@PathVariable long id) {
        GroupDTO groupDTO = service.getById(id);
        if (groupDTO != null) {
            return ResponseEntity.ok(groupDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GroupDTO> add(@RequestBody GroupDTO groupDTO) {
        GroupDTO savedGroupDTO = service.save(groupDTO);
        if (savedGroupDTO != null) {
            return ResponseEntity.ok(savedGroupDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        GroupDTO groupDTO = service.getById(id);
        if (groupDTO != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
