package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.model.Student;
import ua.foxminded.university.repository.RepositoryException;
import ua.foxminded.university.repository.StudentJdbcRepository;

@Service
public class StudentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
	private StudentJdbcRepository dao;

	@Autowired
	public StudentService(StudentJdbcRepository dao) {
		this.dao = dao;
	}

	public boolean add(Student student) throws ServiceException {
		try {
			dao.add(student);
			return true;
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public boolean update(Student student) throws ServiceException {
		try {
			dao.update(student);
			return true;
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteById(long id) throws ServiceException {
		try {
			dao.deleteById(id);
			return true;
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public Student getById(long id) throws ServiceException {
		try {
			return dao.getById(id);
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	public List<Student> getAll() throws ServiceException {
		try {
			return dao.getAll();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage());
			return Collections.emptyList();
		}
	}
}