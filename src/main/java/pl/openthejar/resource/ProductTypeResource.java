package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Product;
import pl.openthejar.model.ProductType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Endpoint typu produktu
 */
@Path("/product-types")
public class ProductTypeResource {

    private EntityDao<ProductType> dao = new EntityDao<>(ProductType.class);
    private EntityDao<Product> productDao = new EntityDao<>(Product.class);

    /**
     * Pobiera wszystkie typy produktow
     * @return Lista wszystkich typow produktow
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductType> getAll() {
        return dao.findAll();
    }

    /**
     * Zapisuje nowy typ produktow w bazie danych
     * @param productType Typ produktow do zapisania
     * @param add Poczatkowa ilosc produktow
     * @return Zapisany typ produktu
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ProductType save(ProductType productType, @QueryParam("add") Integer add) {
        dao.save(productType);
        if (add != null) {
            productDao.saveOrUpdate(new Product(productType, add));
        }

        return productType;
    }
}
