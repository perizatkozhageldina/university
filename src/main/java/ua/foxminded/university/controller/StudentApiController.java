package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentApiController {
    private StudentService service;

    @Autowired
    public StudentApiController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> viewAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Student> viewOne(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student) {
        service.save(student);
        Optional<Student> savedStudent = service.getById(student.getId());
        return savedStudent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Optional<Student> optionalStudent = service.getById(updatedStudent.getId());
        if (optionalStudent.isPresent()) {
            Student existingStudent  = optionalStudent.get();
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setSurname(updatedStudent.getSurname());
            existingStudent.setAcademicYear(updatedStudent.getAcademicYear());
            existingStudent.setGroupId(updatedStudent.getGroupId());
            Student savedStudent = service.save(existingStudent);
            return ResponseEntity.ok(savedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional<Student> studentOptional = service.getById(id);
        if (studentOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
