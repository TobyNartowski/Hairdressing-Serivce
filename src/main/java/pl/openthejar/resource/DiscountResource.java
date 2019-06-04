package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Discount;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Endpoint znizki
 */
@Path("/discounts")
public class DiscountResource {

    private EntityDao<Discount> dao = new EntityDao<>(Discount.class);

    /**
     * Pobiera liste wszystkich znizek
     * @return Lista wszystkich znizek
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Discount> getAll() {
        return dao.findAll();
    }

    /**
     * Dodaje nowa znizke do bazy danych
     * @param discount Znizka, ktora ma zostac zapisana
     * @return Zapisany obiekt znizki
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Discount save(Discount discount) {
        return dao.save(discount);
    }

    /**
     * Pobiera jedna znizke o podanym id
     * @param id Identyfikator znizki
     * @return Obiekt znizki
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
     * Usuwa znizke o podanym id
     * @param id Identyfikator znizki
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
