package base;
import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class BaseTestApp {
    protected AndroidDriver driver;
    protected List<String> expectedMenusORG = Arrays.asList("Kiểm kê", "Sửa chữa", "Cấp phát", "Điều chuyển", "Thanh lý", "Chuyển đi", "Thu hồi", "Bảo trì", "In QR", "Ghi chú", "Danh sách TS");
    protected List<String> expectedMenusAM = Arrays.asList("Kiểm kê", "Sửa chữa", "Cấp phát", "Điều chuyển", "Thanh lý", "Chuyển đi", "Thu hồi", "Bảo trì", "In QR", "Ghi chú","Danh sách TS");
    protected List<String> expectedMenusAU = Arrays.asList("Sửa chữa", "Cấp phát", "Điều chuyển","Thu hồi", "Bảo trì", "In QR", "Ghi chú", "Danh sách TS");
    protected List<String> expectedMenusUser = Arrays.asList("In QR", "Danh sách TS");

    @BeforeClass
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "android");
        caps.setCapability("deviceName", "emulator-5554"); // Thay bằng ID thiết bị thật nếu cần
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "vn.com.oceantech.asset_mobile");
        caps.setCapability("appActivity", "vn.com.oceantech.asset_mobile.MainActivity");
//        caps.setCapability(MobileCapabilityType.NO_RESET, true); // Giữ trạng thái đăng nhập

        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), caps);
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Tìm các ô nhập mã cơ sở bằng XPath và nhập giá trị
        List<WebElement> editTexts = driver.findElements(By.xpath("//android.widget.EditText"));
        System.out.println("Số lượng ô nhập liệu tìm thấy: " + editTexts.size());
        if (!editTexts.isEmpty()) {
            editTexts.getFirst().click();
            WebElement inputField = editTexts.getFirst();
            Actions actions = new Actions(driver);
            actions.sendKeys(inputField, "11111").perform();
        } else {
            System.out.println("Không tìm thấy ô nhập liệu nào.");
        }

        // Tìm nút "Xác nhận" và click vào
//        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Xác nhận']")).click(); //dev đang bỏ
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
