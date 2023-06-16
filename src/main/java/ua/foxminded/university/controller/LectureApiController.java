package ua.foxminded.university.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.LectureDTO;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.service.LectureService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lectures")
public class LectureApiController {
    private LectureService service;

    @Autowired
    public LectureApiController(LectureService service) {
        this.service = service;
    }

    @GetMapping
    public List<LectureDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureDTO> getById(@PathVariable long id) {
        LectureDTO lectureDTO = service.getById(id);
        if (lectureDTO != null) {
            return ResponseEntity.ok(lectureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<LectureDTO> add(@RequestBody LectureDTO lectureDTO) {
        LectureDTO savedLectureDTO = service.save(lectureDTO);
        if (savedLectureDTO != null) {
            return ResponseEntity.ok(savedLectureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<LectureDTO> delete(@PathVariable("id") Long id) {
        LectureDTO lectureDTO = service.getById(id);
        if (lectureDTO != null) {
            service.deleteById(id);
            return ResponseEntity.ok(lectureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
