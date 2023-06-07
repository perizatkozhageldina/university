package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupApiController {
    private GroupService service;

    @Autowired
    public GroupApiController(GroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<Group> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getById(@PathVariable long id) {
        Group group = service.getById(id);
        if (group != null) {
            return ResponseEntity.ok(group);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Group> add(@RequestBody Group group) {
        Group savedGroup = service.save(group);
        if (savedGroup != null) {
            return ResponseEntity.ok(savedGroup);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> update(@PathVariable Long id, @RequestBody Group updatedGroup) {
        Group group = service.getById(updatedGroup.getId());
        if (group != null) {
            group.setName(updatedGroup.getName());
            group.setMaxStudents(updatedGroup.getMaxStudents());
            Group savedGroup = service.save(group);
            return ResponseEntity.ok(savedGroup);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Group group = service.getById(id);
        if (group != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
