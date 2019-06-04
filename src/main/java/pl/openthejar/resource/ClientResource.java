package pl.openthejar.resource;

import pl.openthejar.dao.ClientDao;
import pl.openthejar.model.Client;
import pl.openthejar.model.Employee;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Endpoint klienta
 */
@Path("/clients")
public class ClientResource {

    private ClientDao clientDao = new ClientDao();

    /**
     * Pobiera wszystkich klientow
     * @return Lista klientow
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getAll() {
        return clientDao.findAll();
    }

    /**
     * Pobiera jednego klienta o podanym id
     * @param id Identyfikator klienta
     * @return Obiekt klienta
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") Long id) {
        try {
            return Response.ok(clientDao.get(id), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Dodaje nowego klienta
     * @param client Obiekt klienta, ktory ma zostac dodany
     * @return Dodany obiekt klienta
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client save(Client client) {
        return clientDao.save(client);
    }

    /**
     * Autoryzuje klienta
     * @param username Nazwa uzytkownika
     * @param hash Hash uzytkownika
     * @return Obiekt klienta
     */
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

    /**
     * Pobiera wszystkie opinie klienta
     * @param id Identyfikator klienta
     * @return Lista wszystkich opinii klienta
     */
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

    /**
     * Pobiera wszystkie rezerwacje klienta
     * @param id Identyfikator klienta
     * @param done Czy uwzgledniac rezerwacje zakonczone
     * @return Lista rezerwacji klienta
     */
    @GET
    @Path("/{id}/reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservations(@PathParam("id") Long id, @QueryParam("done") Boolean done) {
        try {
            Client client = clientDao.get(id);
            return Response.ok(clientDao.getReservations(client, done), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Usuwa klienta o podanym id
     * @param id Identyfikator klienta
     * @return Poprawny status 200
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") Long id) {
        try {
            clientDao.remove(id);
            return Response.ok().build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
