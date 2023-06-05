package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.GroupService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupApiController {
    private GroupService service;

    @Autowired
    public GroupApiController(GroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<Group> viewAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Group> viewOne(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Group> add(@RequestBody Group group) {
        service.save(group);
        Optional<Group> savedGroup = service.getById(group.getId());
        return savedGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> update(@PathVariable Long id, @RequestBody Group updatedGroup) {
        Optional<Group> optionalGroup = service.getById(updatedGroup.getId());
        if (optionalGroup.isPresent()) {
            Group existingGroup  = optionalGroup.get();
            existingGroup.setName(updatedGroup.getName());
            existingGroup.setMaxStudents(updatedGroup.getMaxStudents());
            Group savedGroup = service.save(existingGroup);
            return ResponseEntity.ok(savedGroup);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional<Group> groupOptional = service.getById(id);
        if (groupOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
