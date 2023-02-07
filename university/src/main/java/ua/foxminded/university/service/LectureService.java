package ua.foxminded.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.dao.LectureJdbcDAO;
import ua.foxminded.university.model.Lecture;

@Service
public class LectureService implements GenericService<Lecture> {

    @Autowired
    private LectureJdbcDAO dao;
    
    public void add(Lecture lecture) {
        dao.add(lecture);
    }
    
    public void deleteById(int id) {
        dao.deleteById(id);
    }
    
    public Lecture getById(int id) {
        return dao.getById(id);
    }
    
    public List<Lecture> getAll() {
        return dao.getAll();
    }
}
