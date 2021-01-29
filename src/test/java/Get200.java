import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class Get200 extends BaseClass {

    @DataProvider
    private Object[][] endPoints() {
        return new Object[][]{{""}, {"/search/repositories?q=java"}, {"/rate_limit"}};
    }

    @Test(dataProvider = "endPoints")
    public void getReturns200(String endPoints) throws IOException {
        returns200(endPoints);
    }
}
