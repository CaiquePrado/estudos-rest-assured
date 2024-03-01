package diving_depth_automatint_rest;

import org.junit.jupiter.api.Test;
import org.testng.Assert;

import diving_depth_automatint_rest.files.payload;
import io.restassured.path.json.JsonPath;

class SumValidationTest {
  

  @Test
  void testSumOfCourses(){
    JsonPath jsonParse = new JsonPath(payload.CoursePrice());
    int count = jsonParse.getInt("courses.size()");


    int sum = 0;

    for (int i = 0; i < count; i++){
      int price = jsonParse.getInt("courses["+i+"].price");
      int copies = jsonParse.get("courses["+i+"].copies");
      int amount = price * copies;
      sum = sum + amount;
    }

    int purchaseAmount = jsonParse.getInt("dashboard.purchaseAmount");
    
    Assert.assertEquals(purchaseAmount, sum);
  }
}
