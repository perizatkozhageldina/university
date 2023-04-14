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

import ua.foxminded.university.model.Group;

@Repository
@Transactional
public class GroupJdbcRepository implements GenericRepository<Group> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Group group) throws RepositoryException {
        entityManager.persist(group);
    }

    @Override
    public void deleteById(long id) throws RepositoryException {
        entityManager.remove(getById(id));
    }

    @Override
    public Group getById(long id) throws RepositoryException {
        return entityManager.find(Group.class, id);
    }

    @Override
    public List<Group> getAll() throws RepositoryException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> query = builder.createQuery(Group.class);
        Root<Group> rootEntry = query.from(Group.class);
        CriteriaQuery<Group> queryList = query.select(rootEntry);
        query.orderBy(builder.asc(rootEntry.get("id")));
        TypedQuery<Group> typedQueryList = entityManager.createQuery(queryList);
        return typedQueryList.getResultList();
    }

    @Override
    public void update(Group group) throws RepositoryException {
        entityManager.merge(group);
    }
}