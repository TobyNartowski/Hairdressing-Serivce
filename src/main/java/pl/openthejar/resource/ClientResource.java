package pl.openthejar.resource;

import pl.openthejar.dao.ClientDao;
import pl.openthejar.model.Client;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/clients")
public class ClientResource {

    private ClientDao clientDao = new ClientDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getAll() {
        return clientDao.findAll();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(Client client) {
        clientDao.save(client);
    }

    @GET
    @Path("/{username}/{hash}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("username") String username, @PathParam("hash") String hash) {
        try {
            Client client = clientDao.authenticateClient(username, hash);
            return Response.ok(client, MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviews(@PathParam("id") Long id) {
        try {
            Client client = clientDao.get(id);
            return Response.ok(clientDao.getReviews(client), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservations(@PathParam("id") Long id) {
        try {
            Client client = clientDao.get(id);
            return Response.ok(clientDao.getReservations(client), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
