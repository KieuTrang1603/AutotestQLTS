package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePageApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait ;

    @FindBy(xpath = "//android.widget.ScrollView/android.view.View[6]/android.view.View/android.view.View[@content-desc]")
    private List<WebElement> menuItems;

    // Khởi tạo các phần tử giao diện bằng Page Factory
    public HomePageApp(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    //Kiem tra thanh menu hien thi dung k
    public boolean isMenuDisplayedCorrectly(List <String> expectedMenus){
        if (menuItems.size() != expectedMenus.size()) {
            System.out.println("Số lượng menu không khớp! Mong đợi: " + expectedMenus.size() + ", Thực tế: " + menuItems.size());
            return false;
        }

        // Kiểm tra từng menu có tồn tại không
        for (String expectedMenu : expectedMenus) {
            boolean menuExists = menuItems.stream()
                    .map(view -> view.getText().trim())
                    .anyMatch(text -> text.contains(expectedMenu));
            if (!menuExists) {
                System.out.println("Không tìm thấy menu: " + expectedMenu);
                return false;
            }
        }
        return true;
    }

    public void setLogOut(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logOutElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.ScrollView/android.view.View[4]"))
        );
        logOutElement.click();
        LogOutPageApp logOutPageApp = new LogOutPageApp(driver);
        logOutPageApp.tapLogOutView();
    }
}
