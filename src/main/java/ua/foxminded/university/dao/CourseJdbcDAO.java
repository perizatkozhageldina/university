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
        entityManager.merge(course);
    }

	@Override
    public List<Course> getAll() throws DAOException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Course> cq = cb.createQuery(Course.class);
	    Root<Course> rootEntry = cq.from(Course.class);
	    CriteriaQuery<Course> all = cq.select(rootEntry);
	    TypedQuery<Course> allQuery = entityManager.createQuery(all);
	    return allQuery.getResultList();
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