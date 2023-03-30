package ua.foxminded.university.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return entityManager.createQuery("from ", Course.class).getResultList();
    }

	@Override
    public void deleteById(long id) throws DAOException {
    	entityManager.remove(entityManager.find(Course.class, id));

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