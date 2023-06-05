package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.service.LectureService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lectures")
public class LectureApiController {
    private LectureService service;

    @Autowired
    public LectureApiController(LectureService service) {
        this.service = service;
    }

    @GetMapping
    public List<Lecture> viewAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Lecture> viewOne(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Lecture> add(@RequestBody Lecture lecture) {
        service.save(lecture);
        Optional<Lecture> savedLecture = service.getById(lecture.getId());
        return savedLecture.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecture> update(@PathVariable Long id, @RequestBody Lecture updatedLecture) {
        Optional<Lecture> optionalLecture = service.getById(updatedLecture.getId());
        if (optionalLecture.isPresent()) {
            Lecture existingLecture  = optionalLecture.get();
            existingLecture.setName(updatedLecture.getName());
            existingLecture.setNumber(updatedLecture.getNumber());
            Lecture savedGroup = service.save(existingLecture);
            return ResponseEntity.ok(savedGroup);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional<Lecture> lectureOptional = service.getById(id);
        if (lectureOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
