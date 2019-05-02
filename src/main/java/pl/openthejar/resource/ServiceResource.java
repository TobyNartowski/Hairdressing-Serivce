package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/services")
public class ServiceResource {

    private EntityDao<Service> dao = new EntityDao<>(Service.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Service> getAll() {
        return dao.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Service save(Service service) {
        return dao.save(service);
    }
}
