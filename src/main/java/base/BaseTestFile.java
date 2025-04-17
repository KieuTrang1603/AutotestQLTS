package base;

import drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.MyUtil;

import java.util.HashMap;
import java.util.Map;

public class BaseTestFile {

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", MyUtil.DOWNLOAD_PATH);
        prefs.put("download.prompt_for_download", false);
        prefs.put("safebrowsing.enabled", true);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://asvn.oceantech.com.vn/session/signin");

        DriverManager.setWebDriver(driver);
    }

    @AfterClass
    public void tearDown() {
        try {
            if (DriverManager.getWebDriver() != null) {
                DriverManager.quitWebDriver();
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đóng WebDriver: " + e.getMessage());
        }
    }
}
