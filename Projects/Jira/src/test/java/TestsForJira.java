import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestsForJira {

    WebDriver driver;

    @BeforeTest
    public void startSuit() throws InterruptedException
    {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Eich\\IdeaProjects\\Hillel\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://jira.hillel.it:8080/secure/Dashboard.jspa");
        testForLogin("autorob", "forautotests", By.xpath("//a[@id='browse_link']"));
    }

//    @DataProvider
//    public Object[][] credentials(){
//        return new Object[][]{
//                {"autorob", "forautotests", By.xpath("//a[@id='browse_link']")},
//                {"autoro", "forautotests", By.xpath("//div[@class='aui-message error']")},
//                {"autorob", "forautotest", By.xpath("//div[@class='aui-message error']")},
//                {"", "", By.xpath("//div[@class='aui-message error']")},
//                {"' OR 1=1", "", By.xpath("//div[@class='aui-message error']")},
//        };
//    }

    public void testForLogin(String login, String pass, By result) throws InterruptedException {
        clearAndWrite(By.xpath("//input[@id='login-form-username']"), login);
        clearAndWrite(By.xpath("//input[@id='login-form-password']"), pass).submit();

        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(result).isEnabled());
    }

    public WebElement clearAndWrite(By selector, String amount){
        WebElement element = driver.findElement(selector);
        element.clear();
        element.sendKeys(amount);
        return element;
    }

    @Test
    public void checkAccessToTheUserList() throws InterruptedException {
        driver.findElement(By.xpath("//a[@id='admin_menu']")).click();
        driver.findElement(By.xpath("//a[@id='admin_users_menu']")).click();

        Thread.sleep(1000);

       if(driver.findElement(By.xpath("//section//h1")).getText().contains("Administrator Access")) {
           driver.findElement(By.xpath("//input[@id='login-form-authenticatePassword']")).sendKeys("forautotests");
           driver.findElement(By.xpath("//input[@id='login-form-authenticatePassword']")).submit();
       }

       Thread.sleep(1000);
       Assert.assertTrue(driver.findElement(By.xpath("//section//h1")).getText().contains("Administration"));

    }

    @AfterMethod
    public void Logout(){
        driver.findElement(By.xpath("//a[@title='User profile for Automation Robert']")).click();
        driver.findElement(By.xpath("//a[@id='log_out']")).click();
    }

    @AfterTest
    public void Quit(){
        driver.close();
    }
}
