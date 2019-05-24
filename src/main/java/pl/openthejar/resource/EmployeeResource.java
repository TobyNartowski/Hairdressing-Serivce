package pl.openthejar.resource;

import pl.openthejar.dao.EmployeeDao;
import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Employee;
import pl.openthejar.model.Reservation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/employees")
public class EmployeeResource {

    private EmployeeDao dao = new EmployeeDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAll() {
        return dao.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Employee save(Employee employee) {
        return dao.save(employee);
    }

    @GET
    @Path("/{id}/reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reservation> todaysReservations(@PathParam("id") Long id) {
        return dao.getAllReservations(dao.get(id));
    }
}
