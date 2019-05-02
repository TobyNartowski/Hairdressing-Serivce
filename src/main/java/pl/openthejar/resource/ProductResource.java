package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
public class ProductResource {

    private EntityDao<Product> dao = new EntityDao<>(Product.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAll() {
        return dao.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Product save(Product product) {
        return dao.save(product);
    }
}
