package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Product;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Endpoint produktu
 */
@Path("/products")
public class ProductResource {

    private EntityDao<Product> dao = new EntityDao<>(Product.class);

    /**
     * Pobiera wszystkie produkty
     * @return Lista wszystkich produktow
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAll() {
        return dao.findAll();
    }

    /**
     * Zapisuje produkt w bazie danych
     * @param product Produkt do zapisania
     * @return Obiekt produktu, ktory zostal zapisany
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Product save(Product product) {
        return dao.save(product);
    }

    /**
     * Uaktualnie proddukt w bazie danych
     * @param product Uautkualniony produkt
     * @return Uaktualniony obiekt
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Product update(Product product) {
        return dao.saveOrUpdate(product);
    }

    /**
     * Pobiera produkt z bazy danych
     * @param id Identyfikator produktu
     * @return Pobrany obiekt produktu
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
     * Usuwa produkt z bazy danych
     * @param id Identyfikator produktu
     * @return Pobrany produkt
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
