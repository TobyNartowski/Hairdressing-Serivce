package pl.openthejar.dao;

import pl.openthejar.model.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EntityDao<T extends BaseEntity> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Class<T> type;

    public EntityDao(Class<T> type) {
        this.type = type;
        entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void save(T object) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(object);
        tx.commit();
    }

    public T get(Long id) {
        return entityManager.find(type, id);
    }

    public void cleanUp() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
