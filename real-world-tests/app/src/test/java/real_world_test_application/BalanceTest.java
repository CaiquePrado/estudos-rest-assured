package real_world_test_application;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class BalanceTest extends BaseTest {

  // @BeforeEach
  //   void login(){
  //   Map<String, String> login = new HashMap<>();
  //   login.put("email", "caiqueprado80@gmail.com");
  //   login.put("senha", "caique1282");

  //   String TOKEN = 
  //   given()
  //       .body(login)
  //   .when()
  //       .post("/signin")
  //   .then()
  //       .statusCode(200)
  //           .extract()
  //               .path("token");

  //     RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);

  //     RestAssured.get("/reset").then().statusCode(200);
  // }
  
    @Test
    void testBarrigarest_WhenGivenAccountBalance_ShoudlReturnCalcAndStatusCode200(){
        Integer CONTA_ID = getAccountId("Conta para saldo");
        given()
        .when()
            .get("/saldo")
        .then()
            .statusCode(200)
              .body("find{it.conta_id == "+CONTA_ID+"}.saldo", equalTo("534.00"));       
    }


  public int getAccountId(String name){
    return RestAssured.get("/contas?nome="+name).then().extract().path("id[0]");
  }
}
