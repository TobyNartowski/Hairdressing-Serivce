package pl.openthejar.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Klasa zarzadzajaca polaczeniem z baza danych
 */
public class DatabaseProxy {

    static EntityManagerFactory entityManagerFactory;
    static EntityManager entityManager;

    /**
     * Inicjalizuje baze danych
     */
    public static void initDatabase() {
        entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Sprzata po polaczeniach bazy danych
     */
    public void cleanUp() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
