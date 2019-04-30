package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Review;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/reviews")
public class ReviewResource {

    private EntityDao<Review> dao = new EntityDao<>(Review.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Review> getAll() {
        return dao.findAll();
    }
}
