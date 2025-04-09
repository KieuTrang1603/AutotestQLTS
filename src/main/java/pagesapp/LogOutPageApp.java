package pagesapp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import popups.LogOutPopupApp;

import java.time.Duration;

public class LogOutPageApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

//    @FindBy(xpath = "//android.view.View[@content-desc='Đăng xuất']")
//    private WebElement logOut_view;
    // Khởi tạo các phần tử giao diện bằng Page Factory
    public LogOutPageApp(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void tapLogOutView() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logOut_view = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Button[@content-desc='Đăng xuất']"))
        );
        logOut_view.click();
        LogOutPopupApp logOutPopupApp = new LogOutPopupApp(driver);
        logOutPopupApp.tapLogOutButton();
    }
}
