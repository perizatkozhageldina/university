package ua.foxminded.university.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teachers")
public class TeacherApiController {
    private TeacherService service;
    private ModelMapper modelMapper;

    @Autowired
    public TeacherApiController(TeacherService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<TeacherDTO> getAll() {
        List<Teacher> teachers = service.getAll();
        return teachers.stream().map(teacher -> modelMapper.map(teacher, TeacherDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getById(@PathVariable long id) {
        Teacher teacher = service.getById(id);
        if (teacher != null) {
            TeacherDTO teacherDTO = modelMapper.map(teacher, TeacherDTO.class);
            return ResponseEntity.ok(teacherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> add(@RequestBody TeacherDTO teacherDTO) {
        Teacher teacher = modelMapper.map(teacherDTO, Teacher.class);
        Teacher savedTeacher = service.save(teacher);
        if (savedTeacher != null) {
            TeacherDTO savedTeacherDTO = modelMapper.map(savedTeacher, TeacherDTO.class);
            return ResponseEntity.ok(savedTeacherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> update(@PathVariable Long id, @RequestBody TeacherDTO updatedTeacherDTO) {
        Teacher updatedTeacher = modelMapper.map(updatedTeacherDTO, Teacher.class);
        Teacher teacher = service.getById(updatedTeacher.getId());
        if (teacher != null) {
            teacher.setName(updatedTeacher.getName());
            teacher.setSurname(updatedTeacher.getSurname());
            teacher.setCategory(updatedTeacher.getCategory());
            teacher.setHours(updatedTeacher.getHours());
            Teacher savedTeacher = service.save(teacher);
            TeacherDTO savedTeacherDTO = modelMapper.map(savedTeacher, TeacherDTO.class);
            return ResponseEntity.ok(savedTeacherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Teacher teacher = service.getById(id);
        if (teacher != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
