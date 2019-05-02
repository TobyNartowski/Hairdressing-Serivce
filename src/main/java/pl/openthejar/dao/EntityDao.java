package pl.openthejar.dao;

import pl.openthejar.model.BaseEntity;

import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class EntityDao<T extends BaseEntity> extends DatabaseProxy {

    String tableName;
    private Class<T> type;

    public EntityDao(Class<T> type) {
        this.type = type;
        entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
        tableName = type.getSimpleName();
    }

    public T save(T object) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(object);
        tx.commit();
        return object;
    }

    public T get(Long id) {
        return entityManager.find(type, id);
    }

    public List<T> findAll() {
        final String query = "SELECT o FROM " + tableName + " o";
        TypedQuery<T> findAllQuery = entityManager.createQuery(query, type);
        return findAllQuery.getResultList();
    }
}
