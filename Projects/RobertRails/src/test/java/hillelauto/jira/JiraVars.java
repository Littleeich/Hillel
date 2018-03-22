package hillelauto.jira;

import hillelauto.Tools;

public interface JiraVars {
    String baseURL = "http://jira.hillel.it:8080/";
    String username = "autorob";
    String password = "forautotests";

    String newIssueSummary = "AutoTest " + Tools.timestamp();
    String attachmentFileLocation = "/Users/Robert/Downloads/";
    String attachmentFileName = "TEST1.pdf";
}