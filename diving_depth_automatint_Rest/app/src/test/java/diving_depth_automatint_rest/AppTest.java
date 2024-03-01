package diving_depth_automatint_rest;

import org.junit.jupiter.api.Test;

import diving_depth_automatint_rest.files.payload;
import io.restassured.path.json.JsonPath;


class AppTest {
   
    @Test
    void test(){
        JsonPath jsonParse = new JsonPath(payload.CoursePrice());
    }
}
