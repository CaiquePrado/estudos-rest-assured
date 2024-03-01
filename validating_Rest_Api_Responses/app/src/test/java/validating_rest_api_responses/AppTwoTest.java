package validating_rest_api_responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import validating_rest_api_responses.files.ReusableMethods;
import validating_rest_api_responses.files.payload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;


class AppTwoTest {

  private String baseURI = "https://rahulshettyacademy.com";
  private String key = "qaclick123";

  @BeforeEach
  public void setup() {
      RestAssured.baseURI = baseURI;
  }

  @Test
  void testStatusCode() {
      given()
          .queryParam("key", key)
          .contentType(ContentType.JSON)
          .body(payload.addPlace())
      .when()
          .post("maps/api/place/add/json")
      .then()
          .assertThat()
          .statusCode(200);
  }

  @Test
  void testResponseBody() {
      given()
          .queryParam("key", key)
          .contentType(ContentType.JSON)
          .body(payload.addPlace())
      .when()
          .post("maps/api/place/add/json")
      .then()
          .assertThat()
          .statusCode(200)
          .body("scope", equalTo("APP"));
  }

  @Test
  void testUpdateAndGetPlace() {
      String response = given()
          .queryParam("key", key)
          .contentType(ContentType.JSON)
          .body(payload.addPlace())
      .when()
          .post("maps/api/place/add/json")
      .then()
          .assertThat()
          .statusCode(200)
          .extract().response().asString();
      
      JsonPath js = ReusableMethods.rawToJson(response);
      String placeId = js.getString("place_id");

      // Update Place
      String newAddress = "Summer Walk, Africa";
      updatePlace(placeId, newAddress);

      // Get Place
      String actualAddress = getPlaceAddress(placeId);

      Assert.assertEquals(newAddress, actualAddress);
  }

  private void updatePlace(String placeId, String newAddress) {
      given()
          .queryParam("key", key)
          .contentType(ContentType.JSON)
          .body("{\r\n" + 
                  "\"place_id\":\""+placeId+"\",\r\n" + 
                  "\"address\":\""+newAddress+"\",\r\n" + 
                  "\"key\":\""+key+"\"\r\n" + 
                  "}").
      when()
          .put("maps/api/place/update/json")
      .then()
          .assertThat()
          .statusCode(200)
          .body("msg", equalTo("Address successfully updated"));
  }

  private String getPlaceAddress(String placeId) {
      String getPlaceResponse = given()
          .queryParam("key", key)
          .queryParam("place_id", placeId)
      .when()
          .get("maps/api/place/get/json")
      .then()
          .assertThat()
          .statusCode(200)
          .extract().response().asString();
      
      JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
      return js1.getString("address");
  }
}

