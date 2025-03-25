package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;
import java.util.List;

public class BaseTestWeb {
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    protected WebDriver driver;
    protected List<String> expectedMenusORG = Arrays.asList("Trang chủ", "Yêu cầu trình duyệt", "Quản lý", "Báo cáo tổng hợp", "Danh mục", "Hệ thống");
    protected List<String> expectedMenusAM = Arrays.asList("Trang chủ", "Yêu cầu trình duyệt", "Quản lý", "Báo cáo tổng hợp", "Danh mục");
    protected List<String> expectedMenusAU = Arrays.asList("Trang chủ", "Quản lý");
    protected List<String> expectedMenusUser = Arrays.asList("Trang chủ", "Danh sách TSCĐ", "Danh sách CCDC", "Quản lý");

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://asvn.oceantech.com.vn/session/signin");
        threadLocalDriver.set(driver);
    }
    public WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    @AfterMethod
    public void tearDown() {
        try {
            WebDriver driver = threadLocalDriver.get();
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đóng WebDriver: " + e.getMessage());
        } finally {
            threadLocalDriver.remove(); // Xóa ThreadLocal
        }
    }
}
