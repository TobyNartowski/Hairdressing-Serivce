package pl.openthejar.resource;

import pl.openthejar.dao.ClientDao;
import pl.openthejar.dao.EntityDao;
import pl.openthejar.dao.ReservationDao;
import pl.openthejar.model.Client;
import pl.openthejar.model.Reservation;
import pl.openthejar.model.Service;
import pl.openthejar.model.WorkDate;

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
    public Response save(@QueryParam("client_id") Long clientId,
                            @QueryParam("service_id") Long serviceId,
                            @QueryParam("workDate_id") Long workDateId) {
        if (clientId == null || serviceId == null || workDateId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            ClientDao clientDao = new ClientDao();
            EntityDao<Service> serviceDao = new EntityDao<>(Service.class);
            EntityDao<WorkDate> workDateDao = new EntityDao<>(WorkDate.class);

            Client client = clientDao.get(clientId);
            Service service = serviceDao.get(serviceId);
            WorkDate workDate = workDateDao.get(workDateId);

            Reservation reservation = new Reservation();
            reservation.setClient(client);
            reservation.setService(service);
            reservation.setWorkDate(workDate);

            return Response.ok(dao.save(reservation), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
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