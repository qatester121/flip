/*
@author vipin yadav
*/

package com.Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.pages.Home;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.library.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBaseClass {

    public static Properties properties;
    public static WebDriver driver;
    public static WebDriverUtils webDriverUtils = new WebDriverUtils();

    public static Date date = new Date();
    public static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
    public static String dt = formatter.format(date);
    public static FileReader fileReader;
    protected final Logger logger = LogManager.getLogger(getClass());
    public static ExtentSparkReporter spark;
    public static ExtentTest test;
    public static ExtentReports extent;
    public static String reportDestination = "reports/report_" + dt + ".html";

    public  static Home homePage;
    public void extentReportSpark() {
        spark = new ExtentSparkReporter(reportDestination);
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser Name", properties.getProperty("BrowserName"));
        extent.setSystemInfo("Environment", properties.getProperty("Environment"));
        spark.config().setDocumentTitle("WebApp Automation Testing Report");
        spark.config().setReportName("WebApp Automation Test Suite");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        spark.config().setTimelineEnabled(Boolean.TRUE);
    }

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        propertiesLoad();
        extentReportSpark();
        autoOpenBrowser();
    }

    @BeforeMethod
    public void pages(){
        homePage=new Home();

    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws EncryptedDocumentException {
        driver.quit();
        extent.flush();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getName() + " test case is failed. " + "<span class='badge badge-danger'> Fail </span>" + result.getThrowable());

        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip(result.getName() + " test case is skipped." + "<span class='badge badge-warning'> Skip </span>");

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass(result.getName() + " test case is Passed." + "<span class='badge badge-success'> Success </span>");
        }
    }

    public void propertiesLoad() throws IOException {

        try {
            fileReader = new FileReader("src/test/resources/QA.properties");
            properties = new Properties();
            properties.load(fileReader);

        } catch (FileNotFoundException ex) {
            test.info("*************************************************");
            test.info("Property file you are looking for does not exist.");
            test.info("*************************************************");
        }
    }

    public void autoOpenBrowser() {

        if (properties.getProperty("BrowserName").equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            System.setProperty("webdriver.chrome.logfile", "./logs/chromeLogs.txt");
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        } else {

            WebDriverManager.chromiumdriver().setup();
            //   driver = new ChromiumDriver();
            System.setProperty("webdriver.chrome.logfile", "./logs/chromeLogs.txt");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

}