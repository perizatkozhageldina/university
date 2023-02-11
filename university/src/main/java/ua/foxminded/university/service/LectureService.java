package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.LectureJdbcDAO;
import ua.foxminded.university.model.Lecture;

@Service
public class LectureService {
    private LectureJdbcDAO dao;

    @Autowired
    public LectureService(LectureJdbcDAO dao) {
        this.dao = dao;
    }
    
    public void add(Lecture lecture) {
        dao.add(lecture);
    }
    
    public void deleteById(long id) {
        dao.deleteById(id);
    }
    
    public Lecture getById(long id) {
        return dao.getById(id);
    }
    
    public List<Lecture> getAll() {
        return dao.getAll();
    }
}