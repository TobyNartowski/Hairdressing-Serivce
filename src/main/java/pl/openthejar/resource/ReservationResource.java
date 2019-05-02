package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Reservation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/reservations")
public class ReservationResource {

    private EntityDao<Reservation> dao = new EntityDao<>(Reservation.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reservation> getAll() {
        return dao.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Reservation save(Reservation reservation) {
        return dao.save(reservation);
    }
}
