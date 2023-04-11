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

import ua.foxminded.university.model.Teacher;

@Repository
@Transactional
public class TeacherJdbcDAO implements GenericDAO<Teacher> {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void add(Teacher teacher) throws DAOException {
		entityManager.persist(teacher);
	}

	@Override
	public void deleteById(long id) throws DAOException {
		entityManager.remove(getById(id));
	}

	@Override
	public Teacher getById(long id) throws DAOException {
		return entityManager.find(Teacher.class, id);
	}

	@Override
	public List<Teacher> getAll() throws DAOException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Teacher> query = builder.createQuery(Teacher.class);
		Root<Teacher> rootEntry = query.from(Teacher.class);
		CriteriaQuery<Teacher> queryList = query.select(rootEntry);
		query.orderBy(builder.asc(rootEntry.get("id")));
		TypedQuery<Teacher> typedQueryList = entityManager.createQuery(queryList);
		return typedQueryList.getResultList();
	}

	@Override
	public void update(Teacher teacher) throws DAOException {
		entityManager.merge(teacher);
	}
}