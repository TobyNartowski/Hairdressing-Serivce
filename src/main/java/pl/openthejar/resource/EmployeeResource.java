package pl.openthejar.resource;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.openthejar.dao.EmployeeDao;
import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Employee;
import pl.openthejar.model.WorkDate;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Endpoint pracownika
 */
@Path("/employees")
public class EmployeeResource {

    private EmployeeDao dao = new EmployeeDao();
    private EntityDao<WorkDate> workDateDao = new EntityDao<>(WorkDate.class);

    /**
     * Pobiera liste wszystkich pracownikow
     * @return Lista wszystkich pracownikow
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAll() {
        return dao.findAll();
    }

    /**
     * Pobiera pracownika o podanym id
     * @param id Identyfikator pracownika
     * @return Pobrany obiekt pracownika z bazy danych
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") Long id) {
        try {
            return Response.ok(dao.get(id), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Zapisuje nowego pracownika w bazie danych
     * @param employee Pracownik do zapisania
     * @return Obiekt zapisany w bazie danych
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Employee save(Employee employee) {
        return dao.save(employee);
    }

    /**
     * Pobiera wszystkie dzisiejsze rezerwacje pracownika
     * @param id Identyfikator pracownika
     * @return Lista wszystkich dzisiejszych rezerwacji pracownika
     */
    @GET
    @Path("/{id}/todays-reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response todaysReservations(@PathParam("id") Long id) {
        try {
            Employee employee = dao.get(id);
            return Response.ok(dao.getTodaysReservations(employee), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Pobiera ilosc wszystkich rezerwacji pracownikow
     * @return Lista ilosci wszystkich rezerwacji pracownikow
     */
    @GET
    @Path("/monthly-reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response monthlyReservations() {
        try {
            JSONArray array = new JSONArray();
            dao.findAll().forEach(e -> {
                JSONObject employeeObject = new JSONObject();
                employeeObject.put("employee", e.getFirstName() + " " + e.getLastName());
                employeeObject.put("reservations", dao.getMonthlyReservations(e).size());
                array.put(employeeObject);
            });

            return Response.ok(array.toString(), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Pobiera wszystkie rezerwacje pracownika
     * @param id Identyfikator pracownika
     * @param done Czy rezerwacje maja byc zakonczone
     * @return Lista wszystkich rezerwacji pracownika
     */
    @GET
    @Path("/{id}/reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReservations(@PathParam("id") Long id, @QueryParam("done") Boolean done) {
        try {
            Employee employee = dao.get(id);
            return Response.ok(dao.getReservations(employee, done), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Usuwa pracownika
     * @param id Identyfikator pracownika
     * @return Poprawny status 200
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") Long id) {
        try {
            Employee employee = dao.get(id);
            workDateDao.findAll().forEach(w -> {
                if (w.getEmployees().remove(employee)) {
                    workDateDao.save(w);
                }
            });
            dao.remove(id);
            return Response.ok().build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
