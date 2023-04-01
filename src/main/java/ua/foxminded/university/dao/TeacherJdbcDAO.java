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
        entityManager.merge(teacher);
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
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
	    Root<Teacher> rootEntry = cq.from(Teacher.class);
	    CriteriaQuery<Teacher> all = cq.select(rootEntry);
	    TypedQuery<Teacher> allQuery = entityManager.createQuery(all);
	    return allQuery.getResultList();    }

    @Override
    public void update(Teacher teacher) throws DAOException {
    	entityManager.merge(teacher);
    }
}