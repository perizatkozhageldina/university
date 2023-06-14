package ua.foxminded.university.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.service.CourseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseApiController {
    private CourseService service;
    private ModelMapper modelMapper;

    @Autowired
    public CourseApiController(CourseService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CourseDTO> getAll() {
        List<Course> courses = service.getAll();
        return courses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable long id) {
        Course course = service.getById(id);
        if (course != null) {
            CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
            return ResponseEntity.ok(courseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CourseDTO> add(@RequestBody CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        Course savedCourse = service.save(course);
        if (savedCourse != null) {
            CourseDTO savedCourseDTO = modelMapper.map(savedCourse, CourseDTO.class);
            return ResponseEntity.ok(savedCourseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable Long id, @RequestBody CourseDTO updatedCourseDTO) {
        Course updatedCourse = modelMapper.map(updatedCourseDTO, Course.class);
        Course course = service.getById(updatedCourse.getId());
        if (course != null) {
            course .setName(updatedCourse.getName());
            course .setHours(updatedCourse.getHours());
            course .setLevel(updatedCourse.getLevel());
            Course savedCourse = service.save(course);
            CourseDTO savedCourseDTO = modelMapper.map(savedCourse, CourseDTO.class);
            return ResponseEntity.ok(savedCourseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Course course = service.getById(id);
        if (course != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}