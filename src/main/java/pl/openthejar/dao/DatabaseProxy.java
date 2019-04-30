package pl.openthejar.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseProxy {

    static EntityManagerFactory entityManagerFactory;
    static EntityManager entityManager;

    public static void initDatabase() {
        entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void cleanUp() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
