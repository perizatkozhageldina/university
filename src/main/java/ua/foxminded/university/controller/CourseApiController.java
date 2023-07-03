package ua.foxminded.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course Controller", description = "API endpoints for managing courses")
public class CourseApiController {
    private CourseService service;

    @Autowired
    public CourseApiController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all courses")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class))))
    public List<CourseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CourseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Course not found")})
    public ResponseEntity<CourseDTO> getById(@PathVariable long id) {
        CourseDTO courseDTO = service.getById(id);
        if (courseDTO != null) {
            return ResponseEntity.ok(courseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new course")
    @ApiResponse(responseCode = "201", description = "Course created", content = @Content(schema = @Schema(implementation = CourseDTO.class)))
    public ResponseEntity<CourseDTO> add(@RequestBody CourseDTO courseDTO) {
        CourseDTO savedCourseDTO = service.save(courseDTO);
        if (savedCourseDTO != null) {
            return ResponseEntity.ok(savedCourseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CourseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Course not found")})
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
    @Operation(summary = "Delete a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted a course"),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Course not found")})
    public ResponseEntity<CourseDTO> delete(@PathVariable("id") Long id) {
        CourseDTO courseDTO = service.getById(id);
        if (courseDTO != null) {
            service.deleteById(id);
            return ResponseEntity.ok(courseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}//