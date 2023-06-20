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
import ua.foxminded.university.dto.StudentDTO;
import ua.foxminded.university.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Controller", description = "API endpoints for managing students")
public class StudentApiController {
    private StudentService service;

    @Autowired
    public StudentApiController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all students")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentDTO.class))))
    public List<StudentDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StudentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Student not found")})
    public ResponseEntity<StudentDTO> getById(@PathVariable long id) {
        StudentDTO studentDTO = service.getById(id);
        if (studentDTO != null) {
            return ResponseEntity.ok(studentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new student")
    @ApiResponse(responseCode = "201", description = "Student created", content = @Content(schema = @Schema(implementation = StudentDTO.class)))
    public ResponseEntity<StudentDTO> add(@RequestBody StudentDTO studentDTO) {
        StudentDTO savedStudentDTO = service.save(studentDTO);
        if (savedStudentDTO != null) {
            return ResponseEntity.ok(savedStudentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StudentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Student not found")})
    public ResponseEntity<StudentDTO> update(@PathVariable Long id, @RequestBody StudentDTO updatedStudentDTO) {
        StudentDTO studentDTO = service.getById(updatedStudentDTO.getId());
        if (studentDTO != null) {
            studentDTO.setName(updatedStudentDTO.getName());
            studentDTO.setSurname(updatedStudentDTO.getSurname());
            studentDTO.setAcademicYear(updatedStudentDTO.getAcademicYear());
            studentDTO.setGroupId(updatedStudentDTO.getGroupId());
            StudentDTO savedStudentDTO = service.save(studentDTO);
            return ResponseEntity.ok(savedStudentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted a student"),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Student not found")})
    public ResponseEntity<StudentDTO> delete(@PathVariable("id") Long id) {
        StudentDTO studentDTO = service.getById(id);
        if (studentDTO != null) {
            service.deleteById(id);
            return ResponseEntity.ok(studentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
