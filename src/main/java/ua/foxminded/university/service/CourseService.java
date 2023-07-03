package ua.foxminded.university.service;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.repository.CourseJdbcRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class CourseService {
    private CourseJdbcRepository dao;
    private ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseJdbcRepository dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public CourseDTO save(@Valid CourseDTO courseDTO) throws ServiceException {
        Course course = modelMapper.map(courseDTO, Course.class);
        return modelMapper.map(dao.save(course), CourseDTO.class);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public CourseDTO getById(long id) throws ServiceException {
        Course course = dao.findById(id).orElseThrow(() -> new ServiceException("Course not found with ID: " + id));
        return modelMapper.map(course, CourseDTO.class);
    }

    public List<CourseDTO> getAll() throws ServiceException {
        return dao.findAll().stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }
}