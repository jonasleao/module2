import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ResponseUtils {

  public static final String BASE_ENDPOINT = "https://api.github.com";
  private static final String accessTokens = "token 9cd17f6da03d375f2f48957dd0ece4550df5dbfd";
  private static final String CREDENTIAL = "";

  public static CloseableHttpClient client;
  public static CloseableHttpResponse response;

  public static String getHeader(CloseableHttpResponse response, String headerName) {
    Header matchedHeader =
        Arrays.stream(response.getAllHeaders())
            .filter(header -> headerName.equalsIgnoreCase(header.getName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Didn't find the header: " + headerName));
    return matchedHeader.getValue();
  }

  public static CloseableHttpResponse getCall() throws IOException {
    return getCall("");
  }

  public static CloseableHttpResponse getCall(String endPoint) throws IOException {
    HttpRequestBase get = new HttpGet(BASE_ENDPOINT + endPoint);
    response = client.execute(get);
    return response;
  }

  public static CloseableHttpResponse optionalCall() throws IOException {
    return optionalCall("");
  }

  public static CloseableHttpResponse optionalCall(String endPoint) throws IOException {
    HttpRequestBase request = new HttpOptions(BASE_ENDPOINT + endPoint);
    response = client.execute(request);
    return response;
  }

  public static CloseableHttpResponse deleteCall(String endPoint) throws IOException {
    HttpRequestBase request = new HttpDelete(BASE_ENDPOINT + endPoint);
    setAccessTokensHeaders(HttpHeaders.AUTHORIZATION, accessTokens, request);
    response = client.execute(request);
    return response;
  }

  public static CloseableHttpResponse postCall(String endPoint,ContentType contentType, String jsonBody) throws IOException {
    HttpPost request = new HttpPost(BASE_ENDPOINT + endPoint);
    setBaseAuthHeaders(HttpHeaders.AUTHORIZATION, CREDENTIAL, request);
    setPostBody(jsonBody,contentType,request);
    response = client.execute(request);
    return response;
  }
  private static HttpRequestBase setPostBody(String jsonBody, ContentType contentType, HttpPost request){
    request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
    return request;
  }

  private static HttpRequestBase setBaseAuthHeaders(
      String headerName, String headerValue, HttpRequestBase request) {
    byte[] encodeAuth = Base64.encodeBase64(headerValue.getBytes(StandardCharsets.UTF_8));
    String authHeader = "Basic " + new String(encodeAuth);
    request.setHeader(headerName, authHeader);
    return request;
  }

  private static HttpRequestBase setAccessTokensHeaders(
      String headerName, String headerValue, HttpRequestBase request) {
    request.setHeader(headerName, headerValue);
    return request;
  }

    private static HttpRequestBase setHeaders(
            String headerName, String headerValue, HttpRequestBase request) {
      request.setHeader(headerName, headerValue);
      return request;
    }

  public static boolean headerIsPresent(CloseableHttpResponse response, String headerName) {
    return Arrays.stream(response.getAllHeaders())
        .anyMatch(header -> header.getName().equalsIgnoreCase(headerName));
  }

  public static Object getValueFor(JSONObject jsonObject, String key) {
    return jsonObject.get(key);
  }

  public static <T> T unmarshall(CloseableHttpResponse response, Class<T> Clazz)
      throws IOException {
    String jsonBody = EntityUtils.toString(response.getEntity());
    return new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .readValue(jsonBody, Clazz);
  }
}
