package pl.openthejar.resource;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pl.openthejar.dao.DatabaseProxy;
import pl.openthejar.model.Discount;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Przeprowadza testy znizek
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiscountResourceTest {

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
        get("/discounts")
                .then()
                .statusCode(200);
    }

    /**
     * Zapisuje przykladowy obiekt
     */
    @Test
    public void testB() {
        Discount discount = new Discount("Regular customer", 10);

        testEntityId = given()
                .contentType("application/json")
                .body(discount)
                .when()
                .post("/discounts")
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
                .get("/discounts/{id}")
                .then()
                .body("id", equalTo(testEntityId))
                .body("name", equalTo("Regular customer"))
                .body("amount", equalTo(10))
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
                .delete("/discounts/{id}")
                .then()
                .statusCode(200);
    }
}