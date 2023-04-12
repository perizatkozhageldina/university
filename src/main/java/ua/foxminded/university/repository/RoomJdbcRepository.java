package ua.foxminded.university.repository;

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
public class RoomJdbcRepository implements GenericRepository<Room> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(Room room) throws RepositoryException {
		entityManager.persist(room);
	}

	@Override
	public void deleteById(long id) throws RepositoryException {
		entityManager.remove(getById(id));
	}

	@Override
	public Room getById(long id) throws RepositoryException {
		return entityManager.find(Room.class, id);
	}

	@Override
	public List<Room> getAll() throws RepositoryException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Room> query = builder.createQuery(Room.class);
		Root<Room> rootEntry = query.from(Room.class);
		CriteriaQuery<Room> queryList = query.select(rootEntry);
		query.orderBy(builder.asc(rootEntry.get("id")));
		TypedQuery<Room> typedQueryList = entityManager.createQuery(queryList);
		return typedQueryList.getResultList();
	}

	@Override
	public void update(Room room) throws RepositoryException {
		entityManager.merge(room);
	}
}