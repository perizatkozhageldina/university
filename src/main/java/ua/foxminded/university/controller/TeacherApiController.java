package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherApiController {
    private TeacherService service;

    @Autowired
    public TeacherApiController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public List<Teacher> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getById(@PathVariable long id) {
        Teacher teacher = service.getById(id);
        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Teacher> add(@RequestBody Teacher teacher) {
        Teacher savedTeacher = service.save(teacher);
        if (savedTeacher != null) {
            return ResponseEntity.ok(savedTeacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        Teacher teacher = service.getById(updatedTeacher.getId());
        if (teacher != null) {
            teacher.setName(updatedTeacher.getName());
            teacher.setSurname(updatedTeacher.getSurname());
            teacher.setCategory(updatedTeacher.getCategory());
            teacher.setHours(updatedTeacher.getHours());
            Teacher savedTeacher = service.save(teacher);
            return ResponseEntity.ok(savedTeacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Teacher teacher = service.getById(id);
        if (teacher != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
