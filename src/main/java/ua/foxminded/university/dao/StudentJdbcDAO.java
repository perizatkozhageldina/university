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

import ua.foxminded.university.model.Student;

@Repository
@Transactional
public class StudentJdbcDAO implements GenericDAO<Student> {
	
	@PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(Student student) throws DAOException {
        entityManager.merge(student);
    }

    @Override
    public void deleteById(long id) throws DAOException {
        entityManager.remove(getById(id));
    }

    @Override
    public Student getById(long id) throws DAOException {
        return entityManager.find(Student.class, id);
    }

    @Override
    public List<Student> getAll() throws DAOException {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Student> cq = cb.createQuery(Student.class);
	    Root<Student> rootEntry = cq.from(Student.class);
	    CriteriaQuery<Student> all = cq.select(rootEntry);
	    TypedQuery<Student> allQuery = entityManager.createQuery(all);
	    return allQuery.getResultList();    }

    @Override
    public void update(Student student) throws DAOException {
    	entityManager.merge(student);
    }
}