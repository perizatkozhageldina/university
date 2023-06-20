package ua.foxminded.university.service;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.repository.TeacherJdbcRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class TeacherService {
    private TeacherJdbcRepository dao;
    private ModelMapper modelMapper;

    @Autowired
    public TeacherService(TeacherJdbcRepository dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public TeacherDTO save(@Valid TeacherDTO teacherDTO) throws ServiceException {
        Teacher teacher = modelMapper.map(teacherDTO, Teacher.class);
        return modelMapper.map(dao.save(teacher), TeacherDTO.class);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public TeacherDTO getById(long id) throws ServiceException {
        Teacher teacher = dao.findById(id).orElseThrow(() -> new ServiceException("Teacher not found with ID:" + id));
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    public List<TeacherDTO> getAll() throws ServiceException {
        return dao.findAll().stream().map(teacher -> modelMapper.map(teacher, TeacherDTO.class)).collect(Collectors.toList());
    }
}