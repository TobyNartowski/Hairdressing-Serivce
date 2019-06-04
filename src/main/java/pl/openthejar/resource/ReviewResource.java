package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.dao.ReservationDao;
import pl.openthejar.model.Reservation;
import pl.openthejar.model.Review;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Endpoint opinii
 */
@Path("/reviews")
public class ReviewResource {

    private EntityDao<Review> dao = new EntityDao<>(Review.class);
    private ReservationDao reservationDao = new ReservationDao();

    /**
     * Pobiera wszystkie opinie
     * @return Lista wszystkich opinii
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Review> getAll() {
        return dao.findAll();
    }

    /**
     * Dodaje nowa opinie
     * @param review Opinia, ktora ma zostac dodana
     * @param id Identyfikator rezerwacji, do ktorej ma byc dodana opinia
     * @return Dodana opinia
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Review review, @QueryParam("reservation") Long id) {
        try {
            Review savedReview;
            if (id != null) {
                Reservation reservation = reservationDao.get(id);
                savedReview = dao.saveOrUpdate(review);
                reservation.setReview(savedReview);
                reservationDao.saveOrUpdate(reservation);
            } else {
                savedReview = dao.saveOrUpdate(review);
            }
            return Response.ok(savedReview, MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();

        }
    }
}
