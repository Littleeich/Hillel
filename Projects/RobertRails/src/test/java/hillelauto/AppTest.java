package hillelauto;

import java.util.Collections;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static WebDriver browser;

    @DataProvider
    public static Object[][] dataProvider() {
        return new Object[][] { { 0, 9, 100, 10 }, { 0, 100, 10000, 1 }, };
    }

    @Test(dataProvider = "dataProvider")
    public static void generateNumber(Integer min, Integer max, Integer total, Integer threshold)
            throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        browser = new ChromeDriver();

        browser.get("http://random.org/integers/");

        clearAndFill(By.cssSelector("input[name=num]"), total.toString());
        clearAndFill(By.cssSelector("input[name=min]"), min.toString());
        clearAndFill(By.cssSelector("input[name=max]"), max.toString()).submit();

        String[] data = browser.findElement(By.cssSelector("pre.data")).getText().trim().split("\\s+");
        HashMap<String, Integer> numberCounter = new HashMap<>();

        for (String n : data)
            numberCounter.put(n, numberCounter.containsKey(n) ? numberCounter.get(n) + 1 : 1);

        Assert.assertTrue(Collections.max(numberCounter.values())
                - Collections.min(numberCounter.values()) <= (total / 100 * threshold));

        Thread.sleep(2000);
        browser.quit();
    }

    private static WebElement clearAndFill(By selector, String data) {
        WebElement element = browser.findElement(selector);
        element.clear();
        element.sendKeys(data);

        return element;
    }
}
