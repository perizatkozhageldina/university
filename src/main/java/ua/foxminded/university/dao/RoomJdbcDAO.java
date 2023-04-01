package ua.foxminded.university.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ua.foxminded.university.model.Room;

@Repository
@Transactional
public class RoomJdbcDAO implements GenericDAO<Room> {

	@PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(Room room) throws DAOException {
        entityManager.merge(room);
    }

    @Override
    public void deleteById(long id) throws DAOException {
        entityManager.remove(getById(id));
    }

    @Override
    public Room getById(long id) throws DAOException {
        return entityManager.find(Room.class, id);
    }

    @Override
    public List<Room> getAll() throws DAOException {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Room> cq = cb.createQuery(Room.class);
	    Root<Room> rootEntry = cq.from(Room.class);
	    CriteriaQuery<Room> all = cq.select(rootEntry);
	    TypedQuery<Room> allQuery = entityManager.createQuery(all);
	    return allQuery.getResultList();    }

    @Override
    public void update(Room room) throws DAOException {
    	entityManager.merge(room);
    }
}