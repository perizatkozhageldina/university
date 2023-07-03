package ua.foxminded.university.service;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.dto.LectureDTO;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.repository.LectureJdbcRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class LectureService {
    private LectureJdbcRepository dao;
    private ModelMapper modelMapper;

    @Autowired
    public LectureService(LectureJdbcRepository dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public LectureDTO save(@Valid LectureDTO lectureDTO) throws ServiceException {
        Lecture lecture = modelMapper.map(lectureDTO, Lecture.class);
        Lecture savedLecture = dao.save(lecture);
        return modelMapper.map(savedLecture, LectureDTO.class);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public LectureDTO getById(long id) throws ServiceException  {
        Lecture lecture = dao.findById(id).orElseThrow(() -> new ServiceException("Lecture not found with ID: " + id));
        return modelMapper.map(lecture, LectureDTO.class);
    }

    public List<LectureDTO> getAll() throws ServiceException {
        return dao.findAll().stream().map(lecture -> modelMapper.map(lecture, LectureDTO.class)).collect(Collectors.toList());
    }
}