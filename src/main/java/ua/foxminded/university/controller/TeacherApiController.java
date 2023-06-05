package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.TeacherService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
public class TeacherApiController {
    private TeacherService service;

    @Autowired
    public TeacherApiController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public List<Teacher> viewAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Teacher> viewOne(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Teacher> add(@RequestBody Teacher teacher) {
        service.save(teacher);
        Optional<Teacher> savedTeacher = service.getById(teacher.getId());
        return savedTeacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        Optional<Teacher> optionalTeacher = service.getById(updatedTeacher.getId());
        if (optionalTeacher.isPresent()) {
            Teacher existingTeacher  = optionalTeacher.get();
            existingTeacher.setName(updatedTeacher.getName());
            existingTeacher.setSurname(updatedTeacher.getSurname());
            existingTeacher.setCategory(updatedTeacher.getCategory());
            existingTeacher.setHours(updatedTeacher.getHours());
            Teacher savedTeacher = service.save(existingTeacher);
            return ResponseEntity.ok(savedTeacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional<Teacher> teacherOptional = service.getById(id);
        if (teacherOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
