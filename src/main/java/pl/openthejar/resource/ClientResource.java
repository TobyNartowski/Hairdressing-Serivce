package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/clients")
public class ClientResource {

    private EntityDao<Client> dao = new EntityDao<>(Client.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getAll() {
        return Arrays.asList(dao.get(1L));
    }
}
