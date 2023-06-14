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
    private ModelMapper modelMapper;

    @Autowired
    public LectureApiController(LectureService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<LectureDTO> getAll() {
        List<Lecture> lectures = service.getAll();
        return lectures.stream().map(lecture -> modelMapper.map(lecture, LectureDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureDTO> getById(@PathVariable long id) {
        Lecture lecture = service.getById(id);
        if (lecture != null) {
            LectureDTO lectureDTO = modelMapper.map(lecture, LectureDTO.class);
            return ResponseEntity.ok(lectureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<LectureDTO> add(@RequestBody LectureDTO lectureDTO) {
        Lecture lecture = modelMapper.map(lectureDTO, Lecture.class);
        Lecture savedLecture = service.save(lecture);
        if (savedLecture != null) {
            LectureDTO savedLectureDTO = modelMapper.map(savedLecture, LectureDTO.class);
            return ResponseEntity.ok(savedLectureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LectureDTO> update(@PathVariable Long id, @RequestBody LectureDTO updatedLectureDTO) {
        Lecture updatedLecture = modelMapper.map(updatedLectureDTO, Lecture.class);
        Lecture lecture = service.getById(updatedLecture.getId());
        if (lecture != null) {
            lecture.setName(updatedLecture.getName());
            lecture.setNumber(updatedLecture.getNumber());
            Lecture savedLecture = service.save(lecture);
            LectureDTO savedLectureDTO = modelMapper.map(savedLecture, LectureDTO.class);
            return ResponseEntity.ok(savedLectureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Lecture lecture = service.getById(id);
        if (lecture != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
