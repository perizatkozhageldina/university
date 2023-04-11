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

import ua.foxminded.university.model.Lecture;

@Repository
@Transactional
public class LectureJdbcDAO implements GenericDAO<Lecture> {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void add(Lecture lecture) throws DAOException {
		entityManager.persist(lecture);
	}

	@Override
	public void deleteById(long id) throws DAOException {
		entityManager.remove(getById(id));
	}

	@Override
	public Lecture getById(long id) throws DAOException {
		return entityManager.find(Lecture.class, id);
	}

	@Override
	public List<Lecture> getAll() throws DAOException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lecture> query = builder.createQuery(Lecture.class);
		Root<Lecture> rootEntry = query.from(Lecture.class);
		CriteriaQuery<Lecture> queryList = query.select(rootEntry);
		query.orderBy(builder.asc(rootEntry.get("id")));
		TypedQuery<Lecture> typedQueryList = entityManager.createQuery(queryList);
		return typedQueryList.getResultList();
	}

	@Override
	public void update(Lecture lecture) throws DAOException {
		entityManager.merge(lecture);
	}
}