package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseApiController {
    private CourseService service;

    @Autowired
    public CourseApiController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<CourseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable long id) {
        CourseDTO courseDTO = service.getById(id);
        if (courseDTO != null) {
            return ResponseEntity.ok(courseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CourseDTO> add(@RequestBody CourseDTO courseDTO) {
        CourseDTO savedCourseDTO = service.save(courseDTO);
        if (savedCourseDTO != null) {
            return ResponseEntity.ok(savedCourseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable Long id, @RequestBody CourseDTO updatedCourseDTO) {
        CourseDTO courseDTO = service.getById(id);
        if (courseDTO != null) {
            courseDTO.setName(updatedCourseDTO.getName());
            courseDTO.setHours(updatedCourseDTO.getHours());
            courseDTO.setLevel(updatedCourseDTO.getLevel());
            CourseDTO savedCourseDTO = service.save(courseDTO);
            return ResponseEntity.ok(savedCourseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        CourseDTO courseDTO = service.getById(id);
        if (courseDTO != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}