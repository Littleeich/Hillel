package hillelauto.jira;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import hillelauto.WebDriverTestBase;

public class JiraTests extends WebDriverTestBase {
    private LoginPage loginPage;
    private IssuePage issuePage;

    @BeforeClass(alwaysRun = true)
    public void initPages() {
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        issuePage = PageFactory.initElements(browser, IssuePage.class);
        System.out.println("Jira Pages Initialized");
    }

    @Test(description = "951. Invalid Login", priority = -1)
    public void failureLogin() {
        loginPage.failureLogin();
    }

    @Test(description = "952. Valid Login", groups = { "Sanity" })
    public void successfulLogin() {
        loginPage.successfulLogin();
    }

    @Test(description = "953. Create issue", dependsOnMethods = { "successfulLogin" }, groups = { "Sanity", "Issues" })
    public void createIssue() {
        issuePage.createIssue();
    }

    @Test(description = "954. Open issue", dependsOnMethods = { "createIssue" }, groups = { "Sanity", "Issues" })
    public void openIssue() {
        issuePage.openIssue();
    }

    @Test(description = "955. Uplaod Attachment", dependsOnMethods = { "openIssue" }, groups = { "Issues.Attachments" })
    public void uploadAttachment() {
        issuePage.uploadAttachment();
    }

    @Test(description = "Download Attachment", dependsOnMethods = { "uploadAttachment" }, groups = {
            "Issues.Attachments", "disabled" })
    public void downloadAttachment() {
        // loginPage.downloadAttachment();
    }
}
