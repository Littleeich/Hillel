import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;

public class OOPtest{
    private WebDriver driver;

    @DataProvider
    public static Object[][] numbers(){
        return new Object[][]{
                {"1000", "0", "10", "20"},
                {"10000", "0", "10", "0"},
                {"500", "0", "30", "35"}
        };
    }

    @Test(dataProvider = "numbers")
    public void checkNumbers(String amount, String min, String max, String rows) {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Eich\\IdeaProjects\\Hillel\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.get("https://www.random.org/integers/");

        clearAndWrite(By.xpath("//input[@name='num']"), amount);
        clearAndWrite(By.xpath("//input[@name='min']"), min);
        clearAndWrite(By.xpath("//input[@name='max']"), max);
        //clearAndWrite(By.xpath("//input[@name='col']"), rows);

        WebElement buttonElement = driver.findElement(By.xpath("//input[@value='Get Numbers']"));
        buttonElement.click();

        WebElement message = driver.findElement(By.xpath("//h2/following-sibling::p[1]"));
        Assert.assertTrue(message.getText().contains("Here are your random numbers:"), message.getText());

        String[] numArray = driver.findElement(By.xpath("//pre[@class='data']")).getText().trim().split("\\s+");
        HashMap<String, Integer> numbersMap = new HashMap<String, Integer>();

        for(String num : numArray)
            numbersMap.put(num, numbersMap.containsKey(num) ? numbersMap.get(num) + 1 : 1);

        int intamount = Integer.parseInt(amount);
        Assert.assertTrue(Collections.max(numbersMap.values()) - Collections.min(numbersMap.values()) <=
                (Integer.parseInt(amount) / 100 * Integer.parseInt(rows)));
    }

    public WebElement clearAndWrite(By selector, String amount){
        WebElement element = driver.findElement(selector);
        element.clear();
        element.sendKeys(amount);
        return element;
    }

    @AfterMethod
    private void quitMethod(){
        driver.quit();
    }
}
