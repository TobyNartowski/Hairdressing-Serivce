package pl.openthejar.dao;

import pl.openthejar.model.Client;
import pl.openthejar.model.Reservation;
import pl.openthejar.model.Review;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO klienta
 */
public class ClientDao extends EntityDao<Client> {

    /**
     * Konstruktor tworzacy DAO
     */
    public ClientDao() {
        super(Client.class);
    }

    /**
     * Metoda autoryzujaca uzytkownika
     * @param login login uzytkownika
     * @param hash hash uzytkownika
     * @return Obiekt uzytkownika, ktory zostal pobrany
     */
    public Client authenticateClient(String login, String hash) {
        final String query = "SELECT c FROM " + tableName + " c WHERE c.login = :login AND c.hash = :hash";
        TypedQuery<Client> authQuery = entityManager.createQuery(query, Client.class);
        authQuery.setParameter("login", login);
        authQuery.setParameter("hash", hash);

        return authQuery.getSingleResult();
    }

    /**
     * Pobierz wszystkie opinii klienta
     * @param client Klient, dla ktorego pobierane sa opinie
     * @return Lista opinii klienta
     */
    public List<Review> getReviews(Client client) {
        final String query = "SELECT r FROM Reservation r INNER JOIN r.client c WHERE c.id = :id";
        TypedQuery<Reservation> authQuery = entityManager.createQuery(query, Reservation.class);
        authQuery.setParameter("id", client.getId());

        List<Review> reviews = new ArrayList<>();
        for (Reservation reservation : authQuery.getResultList()) {
            if (reservation.getReview() != null) {
                reviews.add(reservation.getReview());
            }
        }

        return reviews;
    }

    /**
     * Pobiera wszystkie rezerwacje klienta
     * @param client Klient, dla ktorego pobierane sa rezerwacje
     * @param done Parametr, ktory okresla czy pobierac rezerwacje skonczone
     * @return Lista rezerwacji klienta
     */
    public List<Reservation> getReservations(Client client, Boolean done) {
        String query;
        TypedQuery<Reservation> authQuery;
        if (done == null) {
            query = "SELECT r FROM Reservation r INNER JOIN r.client c WHERE c.id = :id";
            authQuery = entityManager.createQuery(query, Reservation.class);
        } else {
            query = "SELECT r FROM Reservation r INNER JOIN r.client c WHERE c.id = :id AND r.done = :done";
            authQuery = entityManager.createQuery(query, Reservation.class);
            authQuery.setParameter("done", done);
        }
        authQuery.setParameter("id", client.getId());

        return authQuery.getResultList();
    }
}
