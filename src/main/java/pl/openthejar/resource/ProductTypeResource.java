package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.ProductType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/product-types")
public class ProductTypeResource {

    private EntityDao<ProductType> dao = new EntityDao<>(ProductType.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductType> getAll() {
        return dao.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ProductType save(ProductType productType) {
        return dao.save(productType);
    }
}
