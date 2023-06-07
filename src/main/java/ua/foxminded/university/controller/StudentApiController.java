package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentApiController {
    private StudentService service;

    @Autowired
    public StudentApiController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable long id) {
        Student student = service.getById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student) {
        Student savedStudent = service.save(student);
        if (savedStudent != null) {
            return ResponseEntity.ok(savedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Student student = service.getById(updatedStudent.getId());
        if (student != null) {
            student.setName(updatedStudent.getName());
            student.setSurname(updatedStudent.getSurname());
            student.setAcademicYear(updatedStudent.getAcademicYear());
            student.setGroupId(updatedStudent.getGroupId());
            Student savedStudent = service.save(student);
            return ResponseEntity.ok(savedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Student student = service.getById(id);
        if (student != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
