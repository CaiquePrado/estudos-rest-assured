package real_world_test_application;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import io.restassured.RestAssured;

@Suite
@SelectClasses({AccountsTest.class, TransactionTest.class, BalanceTest.class, AuthTest.class})
public class SuiteTest extends BaseTest {
  
    @BeforeEach
    void login(){
    Map<String, String> login = new HashMap<>();
    login.put("email", "caiqueprado80@gmail.com");
    login.put("senha", "caique1282");

    String TOKEN = 
    given()
        .body(login)
    .when()
        .post("/signin")
    .then()
        .statusCode(200)
            .extract()
                .path("token");

      RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);

      RestAssured.get("/reset").then().statusCode(200);
  }
}
