import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteAndPost extends BaseClass {

  @DataProvider
  private Object[][] endPoints() {
    return new Object[][] {{""}};
  }

  @Test
  public void DeleteIsSuccessful() throws IOException {
    String endPoint = "/repos/jonasleao/deleteme";
    validateDeleteSuccessful(endPoint);
  }

  @Test
  public void createNewRepository() throws IOException {
    String jsonBody = "{\"name\":\"deleteme\"}";
    ResponseUtils.postCall("/user/repos", ContentType.APPLICATION_JSON,jsonBody);
  }
}
