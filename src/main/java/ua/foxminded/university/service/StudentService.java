package ua.foxminded.university.service;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.dto.StudentDTO;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.repository.StudentJdbcRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class StudentService {
    private StudentJdbcRepository dao;
    private ModelMapper modelMapper;

    @Autowired
    public StudentService(StudentJdbcRepository dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public StudentDTO save(@Valid StudentDTO studentDTO) throws ServiceException {
        Student student = modelMapper.map(studentDTO, Student.class);
        return modelMapper.map(dao.save(student), StudentDTO.class);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public StudentDTO getById(long id) throws ServiceException {
        Student student = dao.findById(id).orElseThrow(() -> new ServiceException("Student not found with ID: " + id));
        return modelMapper.map(student, StudentDTO.class);
    }

    public List<StudentDTO> getAll() throws ServiceException {
        return dao.findAll().stream().map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }
}//