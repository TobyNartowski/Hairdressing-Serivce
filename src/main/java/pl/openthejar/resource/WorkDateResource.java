package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.WorkDate;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Endpoint terminu
 */
@Path("/work-dates")
public class WorkDateResource {

    private EntityDao<WorkDate> dao = new EntityDao<>(WorkDate.class);

    /**
     * Pobiera wszystkie terminy
     * @return Lista wszystkich terminow
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorkDate> getAll() {
        return dao.findAll();
    }

    /**
     * Pobiera jeden termin o podanym id
     * @param id Identyfikator terminu
     * @return Pobrany termin
     */
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

    /**
     * Zapisuje nowy termin w bazie danych
     * @param workDate Obiekt bazy danych do zapisania
     * @return Zapisany termin
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WorkDate save(WorkDate workDate) {
        return dao.save(workDate);
    }

    /**
     * Usuwa termin o podanym id
     * @param id Identyfikator terminu
     * @return Poprawny status 200
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") Long id) {
        try {
            dao.remove(id);
            return Response.ok().build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
