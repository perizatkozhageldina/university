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
    private ModelMapper modelMapper;

    @Autowired
    public StudentApiController(StudentService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<StudentDTO> getAll() {
        List<Student> students = service.getAll();
        return students.stream().map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable long id) {
        Student student = service.getById(id);
        if (student != null) {
            StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
            return ResponseEntity.ok(studentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<StudentDTO> add(@RequestBody StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        Student savedStudent = service.save(student);
        if (savedStudent != null) {
            StudentDTO savedStudentDTO = modelMapper.map(savedStudent, StudentDTO.class);
            return ResponseEntity.ok(savedStudentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable Long id, @RequestBody StudentDTO updatedStudentDTO) {
        Student updatedStudent = modelMapper.map(updatedStudentDTO, Student.class);
        Student student = service.getById(updatedStudent.getId());
        if (student != null) {
            student.setName(updatedStudent.getName());
            student.setSurname(updatedStudent.getSurname());
            student.setAcademicYear(updatedStudent.getAcademicYear());
            student.setGroupId(updatedStudent.getGroupId());
            Student savedStudent = service.save(student);
            StudentDTO savedStudentDTO = modelMapper.map(savedStudent, StudentDTO.class);
            return ResponseEntity.ok(savedStudentDTO);
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
