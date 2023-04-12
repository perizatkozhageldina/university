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

import ua.foxminded.university.model.Student;

@Repository
@Transactional
public class StudentJdbcRepository implements GenericRepository<Student> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(Student student) throws RepositoryException {
		entityManager.persist(student);
	}

	@Override
	public void deleteById(long id) throws RepositoryException {
		entityManager.remove(getById(id));
	}

	@Override
	public Student getById(long id) throws RepositoryException {
		return entityManager.find(Student.class, id);
	}

	@Override
	public List<Student> getAll() throws RepositoryException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> query = builder.createQuery(Student.class);
		Root<Student> rootEntry = query.from(Student.class);
		CriteriaQuery<Student> queryList = query.select(rootEntry);
		query.orderBy(builder.asc(rootEntry.get("id")));
		TypedQuery<Student> typedQueryList = entityManager.createQuery(queryList);
		return typedQueryList.getResultList();
	}

	@Override
	public void update(Student student) throws RepositoryException {
		entityManager.merge(student);
	}
}