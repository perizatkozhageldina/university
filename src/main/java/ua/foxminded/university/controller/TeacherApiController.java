package ua.foxminded.university.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teachers")
public class TeacherApiController {
    private TeacherService service;

    @Autowired
    public TeacherApiController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public List<TeacherDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getById(@PathVariable long id) {
        TeacherDTO teacherDTO = service.getById(id);
        if (teacherDTO != null) {
            return ResponseEntity.ok(teacherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> add(@RequestBody TeacherDTO teacherDTO) {
        TeacherDTO savedTeacherDTO = service.save(teacherDTO);
        if (savedTeacherDTO != null) {
            return ResponseEntity.ok(savedTeacherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> update(@PathVariable Long id, @RequestBody TeacherDTO updatedTeacherDTO) {
        TeacherDTO teacherDTO = service.getById(id);
        if (teacherDTO != null) {
            teacherDTO.setName(updatedTeacherDTO.getName());
            teacherDTO.setSurname(updatedTeacherDTO.getSurname());
            teacherDTO.setCategory(updatedTeacherDTO.getCategory());
            teacherDTO.setHours(updatedTeacherDTO.getHours());
            TeacherDTO savedTeacherDTO = service.save(teacherDTO);
            return ResponseEntity.ok(savedTeacherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TeacherDTO> delete(@PathVariable("id") Long id) {
        TeacherDTO teacherDTO = service.getById(id);
        if (teacherDTO != null) {
            service.deleteById(id);
            return ResponseEntity.ok(teacherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
