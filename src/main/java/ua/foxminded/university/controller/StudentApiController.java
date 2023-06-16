package ua.foxminded.university.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.StudentDTO;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentApiController {
    private StudentService service;

    @Autowired
    public StudentApiController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable long id) {
        StudentDTO studentDTO = service.getById(id);
        if (studentDTO != null) {
            return ResponseEntity.ok(studentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<StudentDTO> add(@RequestBody StudentDTO studentDTO) {
        StudentDTO savedStudentDTO = service.save(studentDTO);
        if (savedStudentDTO != null) {
            return ResponseEntity.ok(savedStudentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
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
