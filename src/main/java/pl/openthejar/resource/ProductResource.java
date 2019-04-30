package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Product;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
