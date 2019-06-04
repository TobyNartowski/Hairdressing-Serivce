package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Product;
import pl.openthejar.model.Service;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Endpoint uslugi
 */
@Path("/services")
public class ServiceResource {

    private EntityDao<Service> dao = new EntityDao<>(Service.class);

    /**
     * Pobiera wszystkie uslugi
     * @return Lista wszystkich uslug
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Service> getAll() {
        return dao.findAll();
    }

    /**
     * Dodaje nowa usluge do bazy danych
     * @param service Usluga, ktora ma zostac dodana
     * @return Dodana usluga
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Service save(Service service) {
        return dao.save(service);
    }

    /**
     * Uaktualnia usluge w bazie danych
     * @param service Uaktualniona usluga
     * @return Uaktualniona usluga
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Service update(Service service) {
        return dao.saveOrUpdate(service);
    }

    /**
     * Pobiera usluge z bazy danych
     * @param id Identyfikator uslugi
     * @return Pobrana usluga
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
     * Usuwa usluge z bazy danych
     * @param id Identyfikator uslugi
     * @return Kod statusu 200
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
