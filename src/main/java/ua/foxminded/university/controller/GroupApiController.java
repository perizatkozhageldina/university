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
    private ModelMapper modelMapper;

    @Autowired
    public GroupApiController(GroupService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<GroupDTO> getAll() {
        List<Group> groups = service.getAll();
        return groups.stream().map(group -> modelMapper.map(group, GroupDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getById(@PathVariable long id) {
        Group group = service.getById(id);
        if (group != null) {
            GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
            return ResponseEntity.ok(groupDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GroupDTO> add(@RequestBody GroupDTO groupDTO) {
        Group group = modelMapper.map(groupDTO, Group.class);
        Group savedGroup = service.save(group);
        if (savedGroup != null) {
            GroupDTO savedGroupDTO = modelMapper.map(savedGroup, GroupDTO.class);
            return ResponseEntity.ok(savedGroupDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> update(@PathVariable Long id, @RequestBody GroupDTO updatedGroupDTO) {
        Group updatedGroup = modelMapper.map(updatedGroupDTO, Group.class);
        Group group = service.getById(updatedGroup.getId());
        if (group != null) {
            group.setName(updatedGroup.getName());
            group.setMaxStudents(updatedGroup.getMaxStudents());
            Group savedGroup = service.save(group);
            GroupDTO savedGroupDTO = modelMapper.map(savedGroup, GroupDTO.class);
            return ResponseEntity.ok(savedGroupDTO);
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
