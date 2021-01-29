import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class Get404 extends BaseClass {

    @DataProvider
    private Object[][] endPoints() {
        return new Object[][]{{"/nonExistingUrl"}};
    }

    @Test(dataProvider = "endPoints")
    public void getReturns404(String endPoints) throws IOException {
        returns404(endPoints);
    }
}
