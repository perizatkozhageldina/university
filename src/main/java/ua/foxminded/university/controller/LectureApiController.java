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
import ua.foxminded.university.dto.LectureDTO;
import ua.foxminded.university.service.LectureService;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@Tag(name = "Lecture Controller", description = "API endpoints for managing lectures")
public class LectureApiController {
    private LectureService service;

    @Autowired
    public LectureApiController(LectureService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all lectures")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LectureDTO.class))))
    public List<LectureDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lecture by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = LectureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Lecture not found")})
    public ResponseEntity<LectureDTO> getById(@PathVariable long id) {
        LectureDTO lectureDTO = service.getById(id);
        if (lectureDTO != null) {
            return ResponseEntity.ok(lectureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new lecture")
    @ApiResponse(responseCode = "201", description = "Lecture created", content = @Content(schema = @Schema(implementation = LectureDTO.class)))
    public ResponseEntity<LectureDTO> add(@RequestBody LectureDTO lectureDTO) {
        LectureDTO savedLectureDTO = service.save(lectureDTO);
        if (savedLectureDTO != null) {
            return ResponseEntity.ok(savedLectureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a lecture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = LectureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Lecture not found")})
    public ResponseEntity<LectureDTO> update(@PathVariable Long id, @RequestBody LectureDTO updatedLectureDTO) {
        LectureDTO lectureDTO = service.getById(id);
        if (lectureDTO != null) {
            lectureDTO.setName(updatedLectureDTO.getName());
            lectureDTO.setNumber(updatedLectureDTO.getNumber());
            LectureDTO savedLectureDTO = service.save(lectureDTO);
            return ResponseEntity.ok(savedLectureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lecture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted a lecture"),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Lecture not found")})
    public ResponseEntity<LectureDTO> delete(@PathVariable("id") Long id) {
        LectureDTO lectureDTO = service.getById(id);
        if (lectureDTO != null) {
            service.deleteById(id);
            return ResponseEntity.ok(lectureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}//
