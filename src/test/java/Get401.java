import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class Get401 extends BaseClass {

    @DataProvider
    private Object[][] endPoints() {
        return new Object[][]{
                {"/user"},
                {"/user/followers"},
                {"/notifications"}
        };
    }

    @Test(dataProvider = "endPoints")
    public void getReturns401(String endPoints) throws IOException {
        returns401(endPoints);
    }

}
