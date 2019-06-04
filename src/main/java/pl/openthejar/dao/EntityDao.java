package pl.openthejar.dao;

import pl.openthejar.model.BaseEntity;

import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Klasa bazowa DAO
 * @param <T> Parametr generyczny okreslajacy klase DAO
 */
public class EntityDao<T extends BaseEntity> extends DatabaseProxy {

    String tableName;
    private Class<T> type;

    /**
     * Konstruktor DAO
     * @param type Typ encji, dla ktorej ma byc utworzone DAO
     */
    public EntityDao(Class<T> type) {
        this.type = type;
        entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
        tableName = type.getSimpleName();
    }

    /**
     * Zapisuje obiekt w bazie danych
     * @param object Obiekt do zapisania
     * @return Obiekt, ktory zostal zapisany
     */
    public T save(T object) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(object);
        tx.commit();
        return object;
    }

    /**
     * Zapisuje lub uaktualnie obiekt w bazie danych
     * @param object Obiekt do zapisania lub uaktualnienia
     * @return Obiekt, ktory zostal zapisany lub uaktualniony
     */
    public T saveOrUpdate(T object) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.merge(object);
        tx.commit();
        return object;
    }

    /**
     * Pobiera obiekt z bazy danych
     * @param id Identyfikator obiektu
     * @return Pobrany obiekt
     */
    public T get(Long id) {
        return entityManager.find(type, id);
    }

    /**
     * Pobiera wszystkie obiekty danego typu
     * @return Wszystkie obiekty z danej tabeli
     */
    public List<T> findAll() {
        final String query = "SELECT o FROM " + tableName + " o";
        TypedQuery<T> findAllQuery = entityManager.createQuery(query, type);
        return findAllQuery.getResultList();
    }

    /**
     * Usuwa obiekt z bazy danych
     * @param id Identyfikator obiektu
     */
    public void remove(Long id) {
        EntityTransaction tx = entityManager.getTransaction();
        T object = entityManager.find(type, id);
        tx.begin();
        entityManager.remove(object);
        tx.commit();
    }
}
