import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataTest {
    DataLog providerData = new DataLog();

    @DataProvider(name = "logs")
    public Object[][] createData1() {
        return new Object[][] {
                { providerData.inline, providerData.outline},
                { providerData.oneLineIn, providerData.oneLineOut},
                { "", "\n"},
                {providerData.randomString, "\n"}
        };
    }

    @Test (dataProvider = "logs")
    public void checkLogCleaner(String before, String after){
        Assert.assertEquals(Reader.LogCleaner(before), after);
    }
}
