
package validating_rest_api_responses;

// import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import validating_rest_api_responses.files.ReusableMethods;
import validating_rest_api_responses.files.payload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;

class AppTest {

    // given - all inputs details
    // when - submit the Api - ressource, http methods
    // then - validade the response
    
  @Test
	void testStatusCode() {
    RestAssured.baseURI = "https://rahulshettyacademy.com";

    given().queryParam("key", "qaclick123").contentType(ContentType.JSON)
        .body(payload.addPlace())
        .when().post("maps/api/place/add/json")
        .then().assertThat().statusCode(200);
	}

	@Test
	void testResponseBody() {
    RestAssured.baseURI = "https://rahulshettyacademy.com";

    given().queryParam("key", "qaclick123").contentType(ContentType.JSON)
        .body(payload.addPlace())
        .when().post("maps/api/place/add/json")
        .then().assertThat().statusCode(200).body("scope", equalTo("APP"));
	}

	@Test
	void test() {
    RestAssured.baseURI = "https://rahulshettyacademy.com";
    String response = given().queryParam("key", "qaclick123")
        .contentType(ContentType.JSON)
        .body(payload.addPlace())
        .when().post("maps/api/place/add/json")
        .then().assertThat().statusCode(200)
        .header("server", "Apache/2.4.52 (Ubuntu)")
        .extract().response().asString();
    
    JsonPath js = ReusableMethods.rawToJson(response); //for parsing Json
    String placeId = js.getString("place_id");
    
    // Update Place
    String newAddress = "Summer Walk, Africa";
    
    given().queryParam("key", "qaclick123").contentType(ContentType.JSON)
        .body("{\r\n" + 
                "\"place_id\":\""+placeId+"\",\r\n" + 
                "\"address\":\""+newAddress+"\",\r\n" + 
                "\"key\":\"qaclick123\"\r\n" + 
                "}").
    when().put("maps/api/place/update/json")
    .then().assertThat().statusCode(200)
    .body("msg", equalTo("Address successfully updated"));
    
    // Get Place
    String getPlaceResponse = given().queryParam("key", "qaclick123")
        .queryParam("place_id", placeId)
        .when().get("maps/api/place/get/json")
        .then().assertThat().statusCode(200)
        .extract().response().asString();
    
    JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
    String actualAddress = js1.getString("address");
    
    Assert.assertEquals(newAddress, actualAddress);
	}
}
