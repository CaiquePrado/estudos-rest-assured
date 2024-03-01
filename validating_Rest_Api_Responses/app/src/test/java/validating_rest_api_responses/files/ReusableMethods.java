package validating_rest_api_responses.files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {

	
	public static JsonPath rawToJson(String response)
	{
		JsonPath js1 =new JsonPath(response);
		return js1;
	}
}
