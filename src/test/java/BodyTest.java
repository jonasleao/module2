import entities.User;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class BodyTest extends BaseClass {

  @DataProvider
  private Object[][] userName() {
    return new Object[][] {
      {"mojombo"}, {"defunkt"}, {"pjhyett"}, {"wycats"}, {"ezmobius"},
    };
  }

  @Test(dataProvider = "userName")
  public void validateUserLogin(String userName) throws IOException {

    User user = ResponseUtils.unmarshall(ResponseUtils.getCall("/users/" + userName), User.class);
    Assert.assertEquals(user.getLogin(), userName);
  }

  @Test()
  public void validateUserId() throws IOException {
    int id = 1;
    User user = ResponseUtils.unmarshall(ResponseUtils.getCall("/users/mojombo"), User.class);
    Assert.assertEquals(user.getId(), id);
  }
}
