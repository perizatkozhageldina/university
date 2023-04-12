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

import ua.foxminded.university.model.Lecture;

@Repository
@Transactional
public class LectureJdbcRepository implements GenericRepository<Lecture> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(Lecture lecture) throws RepositoryException {
		entityManager.persist(lecture);
	}

	@Override
	public void deleteById(long id) throws RepositoryException {
		entityManager.remove(getById(id));
	}

	@Override
	public Lecture getById(long id) throws RepositoryException {
		return entityManager.find(Lecture.class, id);
	}

	@Override
	public List<Lecture> getAll() throws RepositoryException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lecture> query = builder.createQuery(Lecture.class);
		Root<Lecture> rootEntry = query.from(Lecture.class);
		CriteriaQuery<Lecture> queryList = query.select(rootEntry);
		query.orderBy(builder.asc(rootEntry.get("id")));
		TypedQuery<Lecture> typedQueryList = entityManager.createQuery(queryList);
		return typedQueryList.getResultList();
	}

	@Override
	public void update(Lecture lecture) throws RepositoryException {
		entityManager.merge(lecture);
	}
}