package base;

import drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;
import java.util.List;

public class BaseMultiTestWeb {
    @BeforeMethod
    public void setUp() {
        WebDriver driver;
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://asvn.oceantech.com.vn/session/signin");
        DriverManager.setWebDriver(driver);
    }

    @AfterMethod
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
