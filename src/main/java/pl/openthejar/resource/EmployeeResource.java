package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Employee;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/employees")
public class EmployeeResource {

    private EntityDao<Employee> dao = new EntityDao<>(Employee.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAll() {
        return dao.findAll();
    }
}
