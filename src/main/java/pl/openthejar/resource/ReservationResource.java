package pl.openthejar.resource;

import pl.openthejar.dao.ReservationDao;
import pl.openthejar.model.Reservation;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reservations")
public class ReservationResource {

    private ReservationDao dao = new ReservationDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("done") Boolean done) {
        if (done == null) {
            return Response.ok(dao.findAll(), MediaType.APPLICATION_JSON_TYPE).build();
        } else if (done) {
            return Response.ok(dao.getDoneReservations(), MediaType.APPLICATION_JSON_TYPE).build();
        } else {
            return Response.ok(dao.getUndoneReservations(), MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") Long id) {
        try {
            return Response.ok(dao.get(id), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Reservation save(Reservation reservation) {
        return dao.save(reservation);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response done(@PathParam("id") Long id, @QueryParam("done") Boolean done) {
        try {
            Reservation reservation = dao.get(id);

            if (done == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                return Response.ok(dao.changeStatus(reservation, done), MediaType.APPLICATION_JSON_TYPE).build();
            }
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}