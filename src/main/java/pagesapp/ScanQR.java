package pagesapp;

import helpers.PermissionHandler;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ScanQR {
    private final AndroidDriver driver;
    private final WebDriverWait wait ;
    @FindBy(xpath = "//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']")
    private WebElement permission_btn;

    // Khởi tạo các phần tử giao diện bằng Page Factory
    public ScanQR(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void Chupmanhinh(){
        PermissionHandler permissionHandler = new PermissionHandler(driver);
        permissionHandler.handleSystemPermissionPopup();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    //    permissionHandler.handleAllPermissions("capture");

//        permissionHandler.pushQRCodeImage(
//                "C:\\Users\\Administrator\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe",
//                "emulator-5554",
//                "D:\\Tester\\Auto\\Selenium\\Login\\File\\QR-siteVTL.png"
//        );
        permissionHandler.injectQRCodeImage(
                "C:\\Users\\Administrator\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe",
                "emulator-5554",
                "D:\\Tester\\Auto\\Selenium\\Login\\File\\QR-siteVTL.png"
        );
    }
}
