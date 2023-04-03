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

import ua.foxminded.university.model.Group;

@Repository
@Transactional
public class GroupJdbcDAO implements GenericDAO<Group> {
	
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(Group group) throws DAOException {
        entityManager.persist(group);
    }

    @Override
    public void deleteById(long id) throws DAOException {
        entityManager.remove(getById(id));
    }

    @Override
    public Group getById(long id) throws DAOException {
        return entityManager.find(Group.class, id);
    }

    @Override
    public List<Group> getAll() throws DAOException {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Group> cq = cb.createQuery(Group.class);
	    Root<Group> rootEntry = cq.from(Group.class);
	    CriteriaQuery<Group> all = cq.select(rootEntry);
	    cq.orderBy(cb.asc(rootEntry.get("id")));
	    TypedQuery<Group> allQuery = entityManager.createQuery(all);
	    return allQuery.getResultList();    }

    @Override
    public void update(Group group) throws DAOException {
    	entityManager.merge(group);
    }
}