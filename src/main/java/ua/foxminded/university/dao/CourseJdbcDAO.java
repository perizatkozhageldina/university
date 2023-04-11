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

import ua.foxminded.university.model.Course;

@Repository
@Transactional
public class CourseJdbcDAO implements GenericDAO<Course> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(Course course) throws DAOException {
		entityManager.persist(course);
	}

	@Override
	public List<Course> getAll() throws DAOException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> query = builder.createQuery(Course.class);
		Root<Course> rootEntry = query.from(Course.class);
		CriteriaQuery<Course> queryList = query.select(rootEntry);
		query.orderBy(builder.asc(rootEntry.get("id")));
		TypedQuery<Course> typedQueryList = entityManager.createQuery(queryList);
		return typedQueryList.getResultList();
	}

	@Override
	public void deleteById(long id) throws DAOException {
		entityManager.remove(getById(id));

	}

	@Override
	public Course getById(long id) throws DAOException {
		return entityManager.find(Course.class, id);
	}

	@Override
	public void update(Course course) throws DAOException {
		entityManager.merge(course);
	}
}