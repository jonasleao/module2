import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BaseClass {

  @BeforeClass
  public void setUpClass() {
    ResponseUtils.client = HttpClientBuilder.create().build();
  }

  @BeforeMethod
  public void setUp() {
//    ResponseUtils.client = HttpClientBuilder.create().build();
  }
  @AfterClass
  public void teardownClass() throws IOException {
    ResponseUtils.client.close();

  }
  @AfterMethod
  public void teardown() throws IOException {

//    ResponseUtils.client.close();
    ResponseUtils.response.close();
  }

  protected void returns200(String endPoint) throws IOException {
    ResponseUtils.getCall(endPoint);
    assertEquals(ResponseUtils.response.getStatusLine().getStatusCode(), 200);
  }

  protected void returns404(String endPoint) throws IOException {
    ResponseUtils.getCall(endPoint);
    assertEquals(ResponseUtils.response.getStatusLine().getStatusCode(), 404);
  }

  protected void returns401(String endPoint) throws IOException {
    ResponseUtils.getCall(endPoint);
    assertEquals(ResponseUtils.response.getStatusLine().getStatusCode(), 401);
  }

  protected void validateContentType(
      CloseableHttpResponse response, String endPoint, String expectedContentType)
      throws IOException {

    String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
    assertEquals(mimeType, expectedContentType);
  }

  protected void validateHeader(
      CloseableHttpResponse response, String headerName, String expectedValue) {
    String headerValue = ResponseUtils.getHeader(response, headerName);
    assertEquals(headerValue.toLowerCase(), expectedValue.toLowerCase());
  }

  protected void validateHeaderIsPresent(CloseableHttpResponse response, String headerName) {
    boolean isPresent = ResponseUtils.headerIsPresent(response, headerName);
    assertTrue(isPresent, headerName + " Header is not present");
  }

  protected String getMethodsListFromOptionals() throws IOException {
    String header = "Access-Control-Allow-Methods";
    return ResponseUtils.getHeader(ResponseUtils.optionalCall(), header);
  }

  protected void validateDeleteSuccessful(String endPoint) throws IOException {
    CloseableHttpResponse response = ResponseUtils.deleteCall(endPoint);
    assertEquals(response.getStatusLine().getStatusCode(), 204);
  }
}
