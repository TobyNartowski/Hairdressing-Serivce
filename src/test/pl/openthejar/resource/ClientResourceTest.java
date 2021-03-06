package pl.openthejar.resource;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pl.openthejar.dao.DatabaseProxy;
import pl.openthejar.model.Client;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Przeprowadza testy klienta
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientResourceTest {

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
        get("/clients")
                .then()
                .statusCode(200);
    }

    /**
     * Zapisuje przykladowy obiekt
     */
    @Test
    public void testB() {
        Client client = new Client("FirstName", "LastName", 123123123L);
        client.setLogin("login@email.com");
        client.setHash("hash");

        testEntityId = given()
                .contentType("application/json")
                .body(client)
                .when()
                .post("/clients")
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
                .get("/clients/{id}")
                .then()
                .body("id", equalTo(testEntityId))
                .body("login", equalTo("login@email.com"))
                .body("hash", equalTo("hash"))
                .body("firstName", equalTo("FirstName"))
                .body("lastName", equalTo("LastName"))
                .body("phoneNumber", equalTo(123123123))
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
                .delete("/clients/{id}")
                .then()
                .statusCode(200);
    }
}