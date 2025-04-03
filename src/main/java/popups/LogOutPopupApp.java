package popups;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogOutPopupApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

//    @FindBy(xpath = "//android.widget.Button[@content-desc='Đăng xuất']")
//    private WebElement logOut_button;
    // Khởi tạo các phần tử giao diện bằng Page Factory
    public LogOutPopupApp(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void tapLogOutButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logOut_button = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Button[@content-desc='Đăng xuất']"))
        );
        logOut_button.click();
    }
}
