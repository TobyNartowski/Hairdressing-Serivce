package pl.openthejar.resource;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pl.openthejar.dao.DatabaseProxy;
import pl.openthejar.model.Service;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceResourceTest {

    private static Integer testEntityId;

    @BeforeClass
    public static void init() {
        DatabaseProxy.initDatabase();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api";
    }

    @Test
    public void testA() {
        get("/services")
                .then()
                .statusCode(200);
    }

    @Test
    public void testB() {
        Service service = new Service("ServiceName", 15, 10, 2);

        testEntityId = given()
                .contentType("application/json")
                .body(service)
                .when()
                .post("/services")
                .then()
                .extract()
                .path("id");
    }

    @Test
    public void testC() {
        given().pathParam("id", testEntityId)
                .when()
                .get("/services/{id}")
                .then()
                .body("id", equalTo(testEntityId))
                .body("name", equalTo("ServiceName"))
                .body("duration", equalTo(15))
                .body("price", equalTo(10))
                .body("need", equalTo(2))
                .and()
                .statusCode(200);
    }

    @Test
    public void testD() {
        given().pathParam("id", testEntityId)
                .when()
                .delete("/services/{id}")
                .then()
                .statusCode(200);
    }
}