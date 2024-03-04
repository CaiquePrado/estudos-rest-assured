package real_world_test_application;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import real_world_test_application.utils.Movimentation;

public class TransactionTest extends BaseTest {

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
    void testBarrigarest_WhenCreateATransaction_ShoudlReturnStatusCode201(){
        Movimentation movimentation = getValidMovimentation();

        given()
              .body(movimentation)
        .when()
            .post("/transacoes")
        .then()
            .statusCode(201);
    }

    @Test
    void testBarrigarest_WhenGivenAEmptyBody_ShoudlReturnValidationsAndStatusCode400(){
        given()
              .body("{}")
        .when()
            .post("/transacoes")
        .then()
            .statusCode(400)
              .body("$", hasSize(8));
                // .body("msg", hasItems(
                //   "Data da Movimentação é obrigatório",
                //            "Data do pagamento é obrigatório",
                //            "Descriçãoo é obrigatório",
                //            "Interessado é obrigatório",
                //            "Valor é obrigatório",
                //            "Valor deve ser um número",
                //            "Conta é obrigatório",
                //            "Situação é obrigatório"
                // ));
    }

    @Test
    void testBarrigarest_WhenCreateATransactionWithFutureTransactionDate_ShoudlReturnStatusCode400(){
        Movimentation movimentation = getValidMovimentation();
        movimentation.setData_transacao("20/05/2060");

        given()
              .body(movimentation)
        .when()
            .post("/transacoes")
        .then()
            .statusCode(400)
              .body("$", hasSize(1));
                // .body("msg", hasItem("Data da movimentação deve ser menor ou igual a data atual"));
    }

    @Test
    void testBarrigarest_WhenDeleteAccount_ShoudlReturnStatusCode200(){
        given()
            .pathParam("id", getAccountId("Conta com movimentacao"))
        .when()
            .delete("/contas/{id}")
        .then()
            .statusCode(500)
              .body("constraint", equalTo("transacoes_conta_id_foreign"));       
    }

    @Test
    void testBarrigarest_WhenRemoveMovimentation_ShoudlReturnCalcAndStatusCode200(){
        given()
            .pathParam("id", getMovimentationId("Movimentacao para exclusao"))
        .when()
            .delete("/transacoes/{id}")
        .then()
            .statusCode(204);    
    }

    public int getAccountId(String name){
      return RestAssured.get("/contas?nome="+name).then().extract().path("id[0]");
    }

    public int getMovimentationId(String description){
      return RestAssured.get("/transacoes?descricao="+description).then().extract().path("id[0]");
    }

    private Movimentation getValidMovimentation(){
      Movimentation movimentation = new Movimentation();
        movimentation.setConta_id(getAccountId("Conta para movimentacoes"));
        movimentation.setDescricao("Descrição da movimentação");
        movimentation.setEnvolvido("Envolvido na movimentacao");
        movimentation.setTipo("REC");
        movimentation.setData_transacao("01/01/2000");
        movimentation.setData_pagamento("10/05/2010");
        movimentation.setValor(100f);
        movimentation.setStatus(true);

        return movimentation;
    }
}
