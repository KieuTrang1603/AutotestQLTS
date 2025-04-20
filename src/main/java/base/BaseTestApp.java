package base;
import drivers.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import utils.SystemHelpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class BaseTestApp {
    private AppiumDriverLocalService service;
    private String HOST = "127.0.0.1";
    private String PORT = "4723";
    private int TIMEOUT_SERVICE = 60;

    @BeforeSuite
    public void runAppiumServer() {
        //Kill process on port
        SystemHelpers.killProcessOnPort("");

        //Build the Appium service
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(HOST);
        builder.usingPort(Integer.parseInt(PORT));
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "info"); // Set log level (optional)
        builder.withTimeout(Duration.ofSeconds(TIMEOUT_SERVICE));

        //Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service.start();

        if (service.isRunning()) {
            System.out.println("##### Appium server started on " + HOST + ":" + PORT);
        } else {
            System.out.println("Failed to start Appium server.");
        }

    }

    @BeforeTest
    public void setUpDriver() {
        AndroidDriver driver;
        UiAutomator2Options options = new UiAutomator2Options();

        System.out.println("***SERVER ADDRESS: " + HOST);
        System.out.println("***SERVER POST: " + PORT);

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("emulator-5554");
        options.setAppPackage("vn.com.oceantech.asset_mobile");
        options.setAppActivity("vn.com.oceantech.asset_mobile.MainActivity");
        options.setNoReset(false);
        options.setFullReset(false);

        try {
            driver = new AndroidDriver(new URL("http://" + HOST + ":" + PORT), options);
            DriverManager.setAppiumDriver(driver);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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

    }

    @AfterTest
    public void tearDownDriver() {
        if (DriverManager.getAppiumDriver() != null) {
            DriverManager.quitAppiumDriver();
        }
        if (service != null && service.isRunning()) {
            service.stop();
            System.out.println("##### Appium server stopped.");
        }
    }

//    @BeforeClass
//    public void setup() throws MalformedURLException {
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability("platformName", "android");
//        caps.setCapability("deviceName", "emulator-5554"); // Thay bằng ID thiết bị thật nếu cần
//        caps.setCapability("automationName", "UiAutomator2");
//        caps.setCapability("appPackage", "vn.com.oceantech.asset_mobile");
//        caps.setCapability("appActivity", "vn.com.oceantech.asset_mobile.MainActivity");
////        caps.setCapability(MobileCapabilityType.NO_RESET, true); // Giữ trạng thái đăng nhập
//
////        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), caps);
//        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//        // Tìm các ô nhập mã cơ sở bằng XPath và nhập giá trị
//        List<WebElement> editTexts = driver.findElements(By.xpath("//android.widget.EditText"));
//        System.out.println("Số lượng ô nhập liệu tìm thấy: " + editTexts.size());
//        if (!editTexts.isEmpty()) {
//            editTexts.getFirst().click();
//            WebElement inputField = editTexts.getFirst();
//            Actions actions = new Actions(driver);
//            actions.sendKeys(inputField, "11111").perform();
//        } else {
//            System.out.println("Không tìm thấy ô nhập liệu nào.");
//        }
//
//        // Tìm nút "Xác nhận" và click vào
////        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Xác nhận']")).click(); //dev đang bỏ
//    }
//
//    @AfterClass
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
