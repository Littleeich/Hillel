package hillelauto.listeners;

import hillelauto.Tools;
import hillelauto.reporting.TestRail;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.HashSet;
import java.util.Map;

public class TestListener implements ITestListener {



    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {
//        Map<String, String> xmlparams = context.getCurrentXmlTest().getAllParameters();
//            trReport.startRun(Integer.parseInt(xmlparams.get("testRailProjectId")),
//                    xmlparams.get("testRailRunPrefix") + " Robert Auto - " + Tools.timestamp());
        HashSet<ITestResult> allResults = new HashSet<>();
        allResults.addAll(context.getSkippedTests().getAllResults());
        allResults.addAll(context.getFailedTests().getAllResults());
        allResults.addAll(context.getPassedTests().getAllResults());

        reportToTestRail(allResults, context);
    }

    private void reportToTestRail(HashSet<ITestResult> results, ITestContext context) {

        String baseURL = "https://hillelrob.testrail.io/";
        Map<String, String> xmlparams = context.getCurrentXmlTest().getAllParameters();
        String projectId = xmlparams.get("testRailProjectId");
        String runPrefix = xmlparams.get("testRailRunPrefix");
        String username = "rvalek@intersog.com";
        String password = "hillel";

        if (baseURL.isEmpty()) {
            System.out.println("TestRail URL is not set.");
            return;
        }

        System.out.println("Reporting to " + baseURL);

        TestRail trReport = new TestRail(baseURL);
        trReport.setCreds(username, password);

        try {
            trReport.startRun(Integer.parseInt(projectId), runPrefix + " Eich Auto - " + Tools.timestamp());

            for (ITestResult result : results) {
                String testDescription = result.getMethod().getDescription();
                try {
                    int caseId = Integer.parseInt(testDescription.substring(0, testDescription.indexOf(".")));
                    trReport.setResult(caseId, result.getStatus());
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println(testDescription + " - Case ID missing; not reporting to TestRail.");
                    e.printStackTrace();
                }
            }

            trReport.endRun();
            System.out.println("Sent reports successfully.");
        } catch (Exception e) {
            System.out.println("Failed to send report to TestRail.");
            e.printStackTrace();
        }
    }


    public void onTestStart(ITestResult result) {
         result.getTestContext().getSkippedTests().removeResult(result.getMethod());
    }

    public void onTestSuccess(ITestResult result) {
    }

    public void onTestFailure(ITestResult result) {
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
}