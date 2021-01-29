import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class ResponseHeaders extends BaseClass {

  @DataProvider
  private Object[][] endPoints() {
    return new Object[][] {
      {""},
      {"/search/repositories?q=java"},
      {"/rate_limit"},
      {"/user"},
      {"/user/followers"},
      {"/notifications"},
      {"/nonExistingUrl"}
    };
  }

  @Test(dataProvider = "endPoints")
  public void validateContentTypeIsJson( String endPoints)
      throws IOException {
    CloseableHttpResponse response = ResponseUtils.getCall(endPoints);
    validateContentType(response, endPoints , "application/json");
  }

  @Test
  public void validateServer() throws IOException {
    CloseableHttpResponse response = ResponseUtils.getCall();
    validateHeader(response, "Server", "github.com");
  }

  @Test
  public void xRateLimitIsSixty() throws IOException {
    CloseableHttpResponse response = ResponseUtils.getCall();
    validateHeader(response, "X-RateLimit-Limit", "60");
  }

  @Test
  public void eTagIsPresent() throws IOException {
    CloseableHttpResponse response = ResponseUtils.getCall();
    validateHeaderIsPresent(response, "ETag");
  }

  @Test
  public void optionsReturnsCorrectMethodsList() throws IOException {
    String expectedReply = "GET, POST, PATCH, PUT, DELETE";
    String actualValue = getMethodsListFromOptionals();
    Assert.assertEquals(actualValue, expectedReply);
  }
}
