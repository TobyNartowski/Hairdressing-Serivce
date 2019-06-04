package pl.openthejar.resource;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pl.openthejar.dao.DatabaseProxy;
import pl.openthejar.model.Product;
import pl.openthejar.model.ProductType;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductResourceTest {

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
        get("/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void testB() {
        Product product = new Product(new ProductType("ProductType", 10), 10);

        testEntityId = given()
                .contentType("application/json")
                .body(product)
                .when()
                .post("/products")
                .then()
                .extract()
                .path("id");
    }

    @Test
    public void testC() {
        given().pathParam("id", testEntityId)
                .when()
                .get("/products/{id}")
                .then()
                .body("id", equalTo(testEntityId))
                .body("quantity", equalTo(10))
                .and()
                .statusCode(200);
    }

    @Test
    public void testD() {
        given().pathParam("id", testEntityId)
                .when()
                .delete("/products/{id}")
                .then()
                .statusCode(200);
    }
}