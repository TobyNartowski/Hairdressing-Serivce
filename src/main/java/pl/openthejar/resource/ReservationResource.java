package pl.openthejar.resource;

import pl.openthejar.dao.*;
import pl.openthejar.model.*;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reservations")
public class ReservationResource {

    private ReservationDao dao = new ReservationDao();
    private EntityDao<WorkDate> workDateDao = new EntityDao<>(WorkDate.class);
    private EmployeeDao employeeDao = new EmployeeDao();

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

//    @PUT
//    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response done(@PathParam("id") Long id, @QueryParam("done") Boolean done) {
//        try {
//            Reservation reservation = dao.get(id);
//
//            if (done == null) {
//                return Response.status(Response.Status.BAD_REQUEST).build();
//            } else {
//                return Response.ok(dao.changeStatus(reservation, done), MediaType.APPLICATION_JSON_TYPE).build();
//            }
//        } catch (NoResultException e) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeTime(@PathParam("id") Long id,
                               @QueryParam("date") Long dateId,
                               @QueryParam("employee") Long employeeId) {
        if (dateId == null && employeeId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            Reservation reservation = dao.get(id);

            if (reservation.getWorkDate() != null) {
                Employee oldEmployee = reservation.getWorkDate().getEmployees().iterator().next();
                Employee employee;
                if (employeeId != null) {
                    employee = employeeDao.get(employeeId);
                } else {
                    employee = oldEmployee;
                }
                reservation.getWorkDate().getEmployees().remove(oldEmployee);
                workDateDao.saveOrUpdate(reservation.getWorkDate());

                WorkDate workDate;
                if (dateId != null) {
                    workDate = workDateDao.get(dateId);
                } else {
                    workDate = reservation.getWorkDate();
                }
                workDate.getEmployees().add(employee);
                workDateDao.saveOrUpdate(workDate);

                reservation.setWorkDate(workDate);
                dao.saveOrUpdate(reservation);
                return Response.ok(reservation, MediaType.APPLICATION_JSON_TYPE).build();
            }

            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}