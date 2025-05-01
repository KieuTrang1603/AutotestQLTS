package base;

import drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;
import java.util.List;

public class BaseTestWeb {

    @BeforeClass
    public void setUp() {
//        WebDriver driver;
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("https://asvn.oceantech.com.vn/session/signin");
//        DriverManager.setWebDriver(driver);
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\ChromeDriver114\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Tools\\Chrome114\\chrome-win64\\chrome.exe"); // Đường dẫn đến Chrome 114

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://asvn.oceantech.com.vn/session/signin");
        DriverManager.setWebDriver(driver);
    }

    @AfterClass
    public void tearDown() {
        try {
            WebDriver driver = DriverManager.getWebDriver();
            if (DriverManager.getWebDriver() != null) {
                DriverManager.quitWebDriver();
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đóng WebDriver: " + e.getMessage());
        }
    }
}
