package real_world_test_application;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthTest extends BaseTest {
  
    @Test
    void testBarrigarest_WhenAccessingApiWithoutToken_ShouldReturnStatusCode401(){
        FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
        req.removeHeader("Authorization");

        given()
        .when()
            .get("/contas")
        .then()
            .statusCode(401);
    }
}
