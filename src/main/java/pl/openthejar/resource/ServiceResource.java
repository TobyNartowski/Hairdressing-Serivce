package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
