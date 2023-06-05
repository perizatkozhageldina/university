package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.service.CourseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseApiController {
    private CourseService service;

    @Autowired
    public CourseApiController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Course> viewAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Course> viewOne(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Course> add(@RequestBody Course course) {
        service.save(course);
        Optional<Course> savedCourse = service.getById(course.getId());
        return savedCourse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course updatedCourse) {
        Optional<Course> optionalCourse = service.getById(updatedCourse.getId());
        if (optionalCourse.isPresent()) {
            Course existingCourse  = optionalCourse.get();
            existingCourse .setName(updatedCourse.getName());
            existingCourse .setHours(updatedCourse.getHours());
            existingCourse .setLevel(updatedCourse.getLevel());
            Course savedCourse = service.save(existingCourse);
            return ResponseEntity.ok(savedCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional<Course> courseOptional = service.getById(id);
        if (courseOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}