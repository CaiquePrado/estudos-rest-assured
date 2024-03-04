package real_world_test_application;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class AccountsTest extends BaseTest {

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
    void testBarrigarest_WhenGivenEmailAndPasswordAndToken_ShouldReturnCreateAccountAndReturnStatusCode201(){
        given()
              .body("{ \"nome\": \"Conta inserida\" }")
        .when()
            .post("/contas")
        .then()
            .statusCode(201);
    }

    @Test
    void testBarrigarest_WhenGivenARegisteredAccountAndChangeTheName_ShoudlReturnNewNameAndStatusCode200(){
        given()
              .body("{ \"nome\": \"Conta alterada\" }")
                  .pathParam("id", getAccountId("Conta para alterar"))
        .when()
            .put("/contas/{id}")
        .then()
            .statusCode(200)
                .body("nome", equalTo("Conta alterada"));
    }

    public int getAccountId(String name){
      return RestAssured.get("/contas?nome="+name).then().extract().path("id[0]");
    }

    @Test
    void testBarrigarest_WhenRegisterAnAccountWithSameName_ShouldReturnStatusCode400(){
        given()
              .body("{ \"nome\": \"Conta mesmo nome\" }")
        .when()
            .post("/contas")
        .then()
            .statusCode(400);
                // .body("error", equalTo("Já existe uma conta com esse nome!"));
    }
}
