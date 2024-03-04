package real_world_test_application;

import io.restassured.http.ContentType;

public interface Constants {
  
  final String APP_BASE_URL = "https://barrigarest.wcaquino.me";
  final Integer APP_PORT = 443;
  final String APP_BASE_PATH = ""; // api/v1...

  final ContentType APP_CONTENT_TYPE = ContentType.JSON;

  final Long MAX_TIME_OUT = 5000L;
}
