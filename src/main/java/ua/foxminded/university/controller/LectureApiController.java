package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.service.LectureService;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
public class LectureApiController {
    private LectureService service;

    @Autowired
    public LectureApiController(LectureService service) {
        this.service = service;
    }

    @GetMapping
    public List<Lecture> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecture> getById(@PathVariable long id) {
        Lecture lecture = service.getById(id);
        if (lecture != null) {
            return ResponseEntity.ok(lecture);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Lecture> add(@RequestBody Lecture lecture) {
        Lecture savedLecture = service.save(lecture);
        if (savedLecture != null) {
            return ResponseEntity.ok(savedLecture);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecture> update(@PathVariable Long id, @RequestBody Lecture updatedLecture) {
        Lecture lecture = service.getById(updatedLecture.getId());
        if (lecture != null) {
            lecture.setName(updatedLecture.getName());
            lecture.setNumber(updatedLecture.getNumber());
            Lecture savedLecture = service.save(lecture);
            return ResponseEntity.ok(savedLecture);
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
