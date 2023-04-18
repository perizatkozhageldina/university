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

import ua.foxminded.university.model.Teacher;

@Repository
@Transactional
public class TeacherJdbcRepository implements GenericRepository<Teacher> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Teacher teacher) throws RepositoryException {
        entityManager.persist(teacher);
    }

    @Override
    public void deleteById(long id) throws RepositoryException {
        entityManager.remove(getById(id));
    }

    @Override
    public Teacher getById(long id) throws RepositoryException {
        return entityManager.find(Teacher.class, id);
    }

    @Override
    public List<Teacher> getAll() throws RepositoryException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = builder.createQuery(Teacher.class);
        Root<Teacher> rootEntry = query.from(Teacher.class);
        CriteriaQuery<Teacher> queryList = query.select(rootEntry);
        query.orderBy(builder.asc(rootEntry.get("id")));
        TypedQuery<Teacher> typedQueryList = entityManager.createQuery(queryList);
        return typedQueryList.getResultList();
    }

    @Override
    public void update(Teacher teacher) throws RepositoryException {
        entityManager.merge(teacher);
    }
}