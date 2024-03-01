package diving_depth_automatint_rest;
import org.junit.jupiter.api.Test;

import diving_depth_automatint_rest.files.payload;
import io.restassured.path.json.JsonPath;

class ComplexJsonParseTest {
  
  @Test
  void test(){
    JsonPath jsonParse = new JsonPath(payload.CoursePrice());
    int count = jsonParse.getInt("courses.size()");

    System.out.println(count);

    int amount = jsonParse.getInt("dashboard.purchaseAmount");

    System.out.println(amount);

    String firsTitle = jsonParse.get("courses[0].title");

    System.out.println(firsTitle);

    for( int i = 0; i < count; i++){
      String titles = jsonParse.get("courses["+i+"].title");
      int price = jsonParse.get("courses["+i+"].price");
      int copies = jsonParse.get("courses["+i+"].copies");
      System.out.println(titles + " " + price + " " + copies);
    }

    for( int i = 0; i < count; i++){
     String coursesTitles = jsonParse.get("courses["+i+"].title");
     if(coursesTitles.equalsIgnoreCase("RPA")){
      int copies = jsonParse.get("courses["+i+"].copies");
      System.out.println(copies);
     }
    }
  }
}