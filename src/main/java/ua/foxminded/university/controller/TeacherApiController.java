package ua.foxminded.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.dto.StudentDTO;
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teacher Controller", description = "API endpoints for managing teachers")
public class TeacherApiController {
    private TeacherService service;

    @Autowired
    public TeacherApiController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all teachers")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class))))
    public List<TeacherDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get teacher by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TeacherDTO.class))),
            @ApiResponse(responseCode = "404", description = "Teacher not found")})
    public ResponseEntity<TeacherDTO> getById(@PathVariable long id) {
        TeacherDTO teacherDTO = service.getById(id);
        if (teacherDTO != null) {
            return ResponseEntity.ok(teacherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new teacher")
    @ApiResponse(responseCode = "201", description = "Teacher created", content = @Content(schema = @Schema(implementation = TeacherDTO.class)))
    public ResponseEntity<TeacherDTO> add(@RequestBody TeacherDTO teacherDTO) {
        TeacherDTO savedTeacherDTO = service.save(teacherDTO);
        if (savedTeacherDTO != null) {
            return ResponseEntity.ok(savedTeacherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TeacherDTO.class))),
            @ApiResponse(responseCode = "404", description = "Teacher not found")})
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
    @Operation(summary = "Delete a teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted a teacher"),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")})
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
