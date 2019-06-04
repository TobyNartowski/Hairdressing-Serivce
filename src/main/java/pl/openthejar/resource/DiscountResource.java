package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Discount;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
}
