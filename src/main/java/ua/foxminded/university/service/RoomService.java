package ua.foxminded.university.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.foxminded.university.model.Room;
import ua.foxminded.university.repository.RepositoryException;
import ua.foxminded.university.repository.RoomJdbcRepository;

@Service
public class RoomService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);
	private RoomJdbcRepository dao;

	@Autowired
	public RoomService(RoomJdbcRepository dao) {
		this.dao = dao;
	}

	public boolean add(Room room) throws ServiceException {
		try {
			dao.add(room);
			return true;
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public boolean update(Room room) throws ServiceException {
		try {
			dao.update(room);
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

	public Room getById(long id) throws ServiceException {
		try {
			return dao.getById(id);
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	public List<Room> getAll() throws ServiceException {
		try {
			return dao.getAll();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage());
			return Collections.emptyList();
		}
	}
}