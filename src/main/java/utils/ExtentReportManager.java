package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;

public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance(ITestContext context) {
        if (extent == null) {
            String suiteName = context.getSuite().getName();
            String reportPath = System.getProperty("user.dir") + "/test-output/" + suiteName + "-report.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}
