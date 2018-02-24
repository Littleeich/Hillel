import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestsForJira {
    static WebDriver browser;

    static String baseURL = "http://jira.hillel.it:8080/";
    static String username = "autorob";
    static String password = "forautotests";
    static String userFullname = "Vasya Ivanov";
    static String userNickName = "QA123456";
    static String basicUserEmail = "vasya@gmail.com";
    static String newUserEmail = "NewVasya@inter.com";

    static String newIssueSummary = "AutoTest " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    static String newIssuePath;
    static String fileHash;

    @BeforeTest
    protected static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Eich\\IdeaProjects\\Hillel\\drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito");
        browser = new ChromeDriver(options);
        browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        browser.get(baseURL);
    }

    @Test(description = "Valid Login")
    private void logIn() {
        clearAndFill(By.cssSelector("input#login-form-username"), username);
        clearAndFill(By.cssSelector("input#login-form-password"), password).submit();

        Assert.assertEquals(username,
                browser.findElement(By.cssSelector("a#header-details-user-fullname")).getAttribute("data-username"));
    }

    @Test(description = "Create issue", dependsOnMethods = { "logIn" }, priority = 1)
    private void createTicket() throws InterruptedException {
        browser.findElement(By.cssSelector("a#create_link")).click();

        clearAndFill(By.cssSelector("input#project-field"), "General QA Robert (GQR)\n");

        new FluentWait<WebDriver>(browser).withTimeout(5, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(InvalidElementStateException.class, StaleElementReferenceException.class)
                .until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver browser) {
                        return clearAndFill(By.cssSelector("input#summary"), newIssueSummary);
                    }
                }).submit();

        // browser.findElement(By.cssSelector("a#aui-uid-1")).click();
        // clearAndFill(By.cssSelector("#description"), "hello 123").submit();

        List<WebElement> newIssueLinks = browser.findElements(By.cssSelector("a.issue-created-key"));

        Assert.assertTrue(newIssueLinks.size() != 0);

        newIssuePath = newIssueLinks.get(0).getAttribute("href");
    }

    @Test (description = "Open issue", dependsOnMethods = { "createTicket" }, groups = { "Sanity" })
    private void openTicket()  {
        browser.get(newIssuePath);
        Assert.assertTrue(browser.getTitle().contains(newIssueSummary));
    }

    @Test (dependsOnMethods = {"openTicket"})
    private void uploadFile() throws InterruptedException, IOException, NoSuchAlgorithmException{
        WebElement some = browser.findElement(By.xpath("//input[@type='file']"));
        some.sendKeys("C:\\Users\\Eich\\Desktop\\index.jpg");

        new FluentWait<WebDriver>(browser).withTimeout(5, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class, NotFoundException.class)
                .until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver browser) {
                        return browser.findElement(By.xpath("//a[@class='attachment-title' and @file-preview-title='index.jpg']"));
                    }});

        Assert.assertTrue(browser.findElement(By.xpath("//div[@class='attachment-thumb']/a"))
                .getAttribute("title").contains("index.jpg"));
        fileHash = hashReading("C:\\Users\\Eich\\Desktop\\index.jpg");
    }

    @Test (description = "Download file", dependsOnMethods = {"uploadFile"})
    private void downloadFile() throws InterruptedException, IOException, NoSuchAlgorithmException {
        browser.findElement(By.xpath("//img[@alt='index.jpg']")).click();
        browser.findElement(By.xpath("//a[@original-title='Download']")).click();
        Thread.sleep(1000);
        String temp = hashReading("C:\\Users\\Eich\\Downloads\\" + "index.jpg");
//            System.out.println(temp);
//            System.out.println(fileHash);
        Assert.assertEquals(temp, fileHash);
    }

    @Test (dependsOnMethods = "logIn", priority = 100)
    private void deleteAllMyTests() throws InterruptedException {
        browser.get("http://jira.hillel.it:8080/");
        browser.findElement(By.xpath("//input[@id='quickSearchInput']")).sendKeys("\n");
        browser.findElement(By.xpath("//a[contains(text(),'Reported by me')]")).click();

        List<WebElement> myIssues;
        do {
            myIssues = browser.findElements(By.xpath("//ol[@class='issue-list']/li[*]"));
            Assert.assertTrue(myIssues.size() > 0, "You didn't create any issues!");

            Thread.sleep(1000);

            new FluentWait<WebDriver>(browser).withTimeout(5, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS)
                    .ignoring(WebDriverException.class, StaleElementReferenceException.class)
                    .until(new Function<WebDriver, WebElement>() {
                        public WebElement apply(WebDriver browser) {
                            return browser.findElement(By.xpath("//a[@class='splitview-issue-link']"));
                        }
                    }).click();

            String is_id = browser.findElement(By.xpath("//a[@class='issue-link']")).getAttribute("rel");
            browser.get("http://jira.hillel.it:8080/secure/DeleteIssue!default.jspa?id=" + is_id);
            browser.findElement(By.xpath("//form/div")).submit();

        } while (myIssues.size() > 10);
    }

    @Test (dependsOnMethods = "logIn")
    public void checkAccessToTheUserList() throws InterruptedException {
        browser.findElement(By.xpath("//a[@id='admin_menu']")).click();
        browser.findElement(By.xpath("//a[@id='admin_users_menu']")).click();

        if(browser.findElement(By.xpath("//section//h1")).getText().contains("Administrator Access")) {
            browser.findElement(By.xpath("//input[@id='login-form-authenticatePassword']")).sendKeys("forautotests");
            browser.findElement(By.xpath("//input[@id='login-form-authenticatePassword']")).submit();
        }

        Assert.assertTrue(browser.findElement(By.xpath("//section//h1")).getText().contains("Administration"));
    }

    @Test (dependsOnMethods = "checkAccessToTheUserList")
    public void createUser() {
        browser.findElement(By.xpath("//a[@id='create_user']")).click();

        browser.findElement(By.xpath("//input[@id='user-create-email']")).sendKeys(basicUserEmail);
        browser.findElement(By.xpath("//input[@id='user-create-fullname']")).sendKeys(userFullname);
        browser.findElement(By.xpath("//input[@id='user-create-username']")).sendKeys(userNickName);
        browser.findElement(By.xpath("//input[@id='user-create-submit']")).click();

        List<WebElement> creation = browser.findElements(By.xpath("//span[@class='user-created-flag-single']"));
        if(creation.size() > 0)
            Assert.assertTrue(creation.get(0).getAttribute("data-user-created").contains(userFullname),
                    "Attribute is " + creation.get(0).getAttribute("data-user-created"));
        else
            Assert.assertTrue(false, "No users were created!");
    }

    @Test (dependsOnMethods = "createUser")
    public void editUser(){
        clearAndFill(By.xpath("//input[@name='userSearchFilter']"), userNickName + "\n");
        //a[@id='edituser_link_{username}']
        browser.findElement(By.xpath("//a[@id='edituser_link_" + userNickName + "']")).click();
        clearAndFill(By.xpath("//input[@id='user-edit-email']"), newUserEmail);
        browser.findElement(By.xpath("//input[@id='user-edit-submit']")).submit();

        new FluentWait<WebDriver>(browser).withTimeout(5, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver browser) {
                        return clearAndFill(By.xpath("//input[@name='userSearchFilter']"), userNickName + "\n");
                    }
                });

        Assert.assertTrue(browser.findElement(By.xpath("//span[@class='email']")).getText().contains(newUserEmail),
                "We waited for email: " + newUserEmail + ", but received: " +
                        browser.findElement(By.xpath("//span[@class='email']")).getText());
    }

    @Test (dependsOnMethods = "editUser")
    public void deleteUser() {
        clearAndFill(By.xpath("//input[@name='userSearchFilter']"), userNickName + "\n");
        browser.findElement(By.xpath("//a[@href='#user-actions-" + userNickName + "']")).click();
        browser.findElement(By.xpath("//a[@id='deleteuser_link_"+ userNickName + "']")).click();
        browser.findElement(By.xpath("//form[@id='delete_user_confirm']")).submit();

        new FluentWait<WebDriver>(browser).withTimeout(5, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver browser) {
                        return clearAndFill(By.xpath("//input[@name='userSearchFilter']"), userNickName + "\n");
                    }
                });

        List<WebElement> usersFullnames = browser.findElements(By.xpath("//span[@class='fn']"));
        Assert.assertTrue(usersFullnames.size() == 0,
                "\n\nWe didn't expect to find element after delete operation, but we did\n\n");
    }


    public void scroll(){
        ((JavascriptExecutor) browser).executeScript("window.scrollBy(0,250)");
    }

    public String hashReading(String filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-1");

        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(filePath);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            shaDigest.update(byteArray, 0, bytesCount);
        };

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = shaDigest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }

    @AfterTest
    private void finish() {
       browser.close();
    }

    public static WebElement clearAndFill(By selector, String data) {
        WebElement element = browser.findElement(selector);
        element.clear();
        element.sendKeys(data);

        return element;
    }
}
