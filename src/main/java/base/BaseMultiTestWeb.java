package base;

import drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;
import java.util.List;

public class BaseMultiTestWeb {
    @BeforeMethod
    public void setUp() {
//        WebDriver driver;
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("https://asvn.oceantech.com.vn/session/signin");
//        DriverManager.setWebDriver(driver);

        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\ChromeDriver114\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Tools\\Chrome114\\chrome-win64\\chrome.exe"); // Đường dẫn đến Chrome 114

        // Thêm headless nếu chạy trên runner CI/CD
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
//        driver.get("https://asvn.oceantech.com.vn/session/signin");
        driver.get(BaseTestWeb.LOGIN_URL);
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
