import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
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

    @Test (dataProvider = "logs", description = "checkLog")
    public void checkLogCleaner(String before, String after){
        Assert.assertEquals(Reader.LogCleaner(before), after);
    }

    @AfterMethod
    void afterM (ITestResult testResult){
        System.out.println("Is Succes: " + testResult.isSuccess());
        System.out.println("Host: " + testResult.getHost());
        System.out.println("InstanceName: " + testResult.getInstanceName());
        System.out.println("Name: " + testResult.getName());
        System.out.println("TestName: " + testResult.getTestName());
        System.out.println("Description is: " + testResult.getMethod().getDescription());
        System.out.println("Method is: " + testResult.getMethod().getMethodName());
        System.out.println("ID is: " + testResult.getMethod().getId());
        System.out.println("End time is: " + testResult.getEndMillis());
        System.out.println("Start time is: " + testResult.getStartMillis());
        System.out.println("\n\n");
    }
}
