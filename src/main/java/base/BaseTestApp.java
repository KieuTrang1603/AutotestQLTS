package base;
import drivers.DriverManager;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import helpers.SystemHelpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class BaseTestApp {
    private AppiumDriverLocalService service;
//    private String HOST = "127.0.0.1";
    private String HOST = "192.168.75.1";
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

        // >>> BỔ SUNG DÒNG NÀY để cho phép adb_shell
        builder.withArgument(GeneralServerFlag.ALLOW_INSECURE, "adb_shell");

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
    //    options.setDeviceName("emulator-5554");
        options.setDeviceName("R58M61WSWFV");
        options.setAppPackage("vn.com.oceantech.asset_mobile");
        options.setAppActivity("vn.com.oceantech.asset_mobile.MainActivity");
        options.setCapability("enableImageInjection",true);
        //dung voi may that
        options.setCapability("unicodeKeyboard", true);
        options.setCapability("resetKeyboard", true);

        options.setNoReset(false);
        options.setFullReset(false);

        try {
            driver = new AndroidDriver(new URL("http://" + HOST + ":" + PORT), options);
            DriverManager.setAppiumDriver(driver);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Tìm các ô nhập mã cơ sở bằng XPath và nhập giá trị
        List<WebElement> editTexts = driver.findElements(By.xpath("//android.widget.EditText"));
        System.out.println("Số lượng ô nhập liệu tìm thấy: " + editTexts.size());
        if (!editTexts.isEmpty()) {
//            editTexts.getFirst().click();
//            WebElement inputField = editTexts.getFirst();
////            Actions actions = new Actions(driver);
////            actions.sendKeys(inputField, "11111").perform();

        //dùng máy thật
            WebElement inputField = editTexts.get(0); // lấy ô đầu tiên
            wait.until(ExpectedConditions.elementToBeClickable(inputField));
            inputField.click();
            inputField.clear();
            if (inputField.getText().isEmpty()) {
                System.out.println("Ô input vẫn trống, dùng adb shell để nhập text...");
                driver.executeScript("mobile: shell", Map.of(
                        "command", "input",
//                        "args", List.of("text", "11111")
                        "args", List.of("text", "00000")
                ));
            } else {
                System.out.println("Text đã nhập thành công bằng sendKeys!");
            }
            //hết dùng máy that
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
//    AndroidDriver driver;
//    @BeforeClass
//    public void setup() throws MalformedURLException {
//        AndroidDriver driver;
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability("platformName", "android");
////        caps.setCapability("deviceName", "emulator-5554"); // Thay bằng ID thiết bị thật nếu cần
//        caps.setCapability("deviceName", "3405261611001RE");
//        caps.setCapability("automationName", "UiAutomator2");
//        caps.setCapability("appPackage", "vn.com.oceantech.asset_mobile");
//        caps.setCapability("appActivity", "vn.com.oceantech.asset_mobile.MainActivity");
////        caps.setCapability(MobileCapabilityType.NO_RESET, true); // Giữ trạng thái đăng nhập
//
//        driver = new AndroidDriver(new URL("http://192.168.75.1:4723/wd/hub"), caps);
//        DriverManager.setAppiumDriver(driver);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//        // Tìm các ô nhập mã cơ sở bằng XPath và nhập giá trị
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        // Tìm các ô nhập mã cơ sở bằng XPath và nhập giá trị
//        List<WebElement> editTexts = driver.findElements(By.xpath("//android.widget.EditText"));
//        System.out.println("Số lượng ô nhập liệu tìm thấy: " + editTexts.size());
//        if (!editTexts.isEmpty()) {
////            editTexts.getFirst().click();
////            WebElement inputField = editTexts.getFirst();
//////            Actions actions = new Actions(driver);
//////            actions.sendKeys(inputField, "11111").perform();
//
//        //dùng máy thật
//            WebElement inputField = editTexts.get(0); // lấy ô đầu tiên
//            wait.until(ExpectedConditions.elementToBeClickable(inputField));
//            inputField.click();
//            inputField.clear();
//            if (inputField.getText().isEmpty()) {
//                System.out.println("Ô input vẫn trống, dùng adb shell để nhập text...");
//                driver.executeScript("mobile: shell", Map.of(
//                        "command", "input",
////                        "args", List.of("text", "11111")
//                        "args", List.of("text", "00000")
//                ));
//            } else {
//                System.out.println("Text đã nhập thành công bằng sendKeys!");
//            }
//            //hết dùng máy that
//        } else {
//            System.out.println("Không tìm thấy ô nhập liệu nào.");
//        }
//
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
