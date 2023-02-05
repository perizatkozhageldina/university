package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.university.dao.LectureJdbcDAO;
import ua.foxminded.university.model.Lecture;

public class LectureService {

    @Autowired
    private LectureJdbcDAO dao;
    
    public void addLecture(Lecture lecture) {
        dao.add(lecture);
    }
    
    public void deleteLecture(Lecture lecture) {
        dao.deleteById(lecture.getLectureId());
    }
    
    public Lecture getLectureById(int id) {
        return dao.getById(id);
    }
    
    public List<Lecture> getAllLectures() {
        return dao.getAll();
    }
}
