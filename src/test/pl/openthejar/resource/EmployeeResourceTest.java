package pl.openthejar.resource;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pl.openthejar.dao.DatabaseProxy;
import pl.openthejar.model.Employee;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Przeprowadza testy pracownika
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeResourceTest {

    private static Integer testEntityId;

    /**
     * Inicjacja bazy danych oraz rest
     */
    @BeforeClass
    public static void init() {
        DatabaseProxy.initDatabase();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api";
    }

    /**
     * Pobiera wszystkie obiekty
     */
    @Test
    public void testA() {
        get("/employees")
                .then()
                .statusCode(200);
    }

    /**
     * Zapisuje przykladowy obiekt
     */
    @Test
    public void testB() {
        Employee employee = new Employee("EmployeeLogin", "Password", "FirstName", "LastName");

        testEntityId = given()
                .contentType("application/json")
                .body(employee)
                .when()
                .post("/employees")
                .then()
                .extract()
                .path("id");
    }

    /**
     * Pobiera przykladowy obiekt i sprawdza jego pola
     */
    @Test
    public void testC() {
        given().pathParam("id", testEntityId)
                .when()
                .get("/employees/{id}")
                .then()
                .body("id", equalTo(testEntityId))
                .body("login", equalTo("EmployeeLogin"))
                .body("hash", equalTo("Password"))
                .body("firstName", equalTo("FirstName"))
                .body("lastName", equalTo("LastName"))
                .and()
                .statusCode(200);
    }

    /**
     * Usuwa przykladowy obiekt z bazy danych
     */
    @Test
    public void testD() {
        given().pathParam("id", testEntityId)
                .when()
                .delete("/employees/{id}")
                .then()
                .statusCode(200);
    }
}
