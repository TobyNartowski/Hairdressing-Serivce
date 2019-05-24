package pl.openthejar.dao;

import pl.openthejar.model.Client;
import pl.openthejar.model.Reservation;
import pl.openthejar.model.Review;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ClientDao extends EntityDao<Client> {

    public ClientDao() {
        super(Client.class);
    }

    public Client authenticateClient(String login, String hash) {
        final String query = "SELECT c FROM " + tableName + " c WHERE c.login = :login AND c.hash = :hash";
        TypedQuery<Client> authQuery = entityManager.createQuery(query, Client.class);
        authQuery.setParameter("login", login);
        authQuery.setParameter("hash", hash);

        return authQuery.getSingleResult();
    }

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

    public List<Reservation> getReservations(Client client) {
        final String query = "SELECT r FROM Reservation r INNER JOIN r.client c WHERE c.id = :id";
        TypedQuery<Reservation> authQuery = entityManager.createQuery(query, Reservation.class);
        authQuery.setParameter("id", client.getId());

        return authQuery.getResultList();
    }
}
