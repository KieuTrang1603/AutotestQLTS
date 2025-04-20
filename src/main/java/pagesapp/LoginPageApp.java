package pagesapp;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

public class LoginPageApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Khởi tạo các phần tử giao diện bằng Page Factory
    public LoginPageApp(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Định nghĩa các phần tử trên màn hình
    @FindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.EditText[1]")
    private WebElement usernameField;

    @FindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.EditText[2]")
    private WebElement passwordField;

    @FindBy(xpath = "//android.widget.Button[@content-desc = 'Đăng nhập']")
    private WebElement loginButton;

//    @FindBy(xpath = "(//android.widget.TextView)[last()]")
//    private WebElement errorMessage;

    // Phương thức nhập tên đăng nhập
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.click();
        usernameField.clear();
        usernameField.sendKeys(username);
        wait.until(driver -> usernameField.getAttribute("text").equals(username));
    //    wait.until(ExpectedConditions.attributeToBeNotEmpty(usernameField, "text"));
    }

    // Phương thức nhập mật khẩu
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.click();
        passwordField.clear();
        passwordField.sendKeys(password);
    //    wait.until(ExpectedConditions.textToBePresentInElementValue(passwordField, password));
    //    wait.until(ExpectedConditions.attributeToBeNotEmpty(passwordField, "text"));
    }

    // Phương thức nhấn nút đăng nhập
    public void tapLoginButton() {
        loginButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Phương thức lấy thông báo lỗi (nếu có)
//    public String getErrorMessage() {
//        try {
//            WebElement error = new WebDriverWait(driver, Duration.ofSeconds(5))
//                    .until(ExpectedConditions.presenceOfElementLocated(
//                            AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Bạn cần điền đầy đủ tài khoản mật khẩu')]")
//                            AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Tài khoản mật khẩu không chính xác')]")
//                    ));
//            return error.getText();
//        } catch (Exception e) {
//            return "Không tìm thấy thông báo lỗi";
//        }
//    }

    public boolean isErrorMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> elements = driver.findElements(By.xpath("//*[contains(@text, 'Tài khoản mật khẩu không chính xác')]"));
            for (WebElement element : elements) {
                System.out.println("Element found: " + element.getText());
            }
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.view.View[contains(@text, 'Tài khoản mật khẩu không chính xác')]")
            ));
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            List<WebElement> elements = driver.findElements(By.xpath("//*[contains(@text, 'Tài khoản mật khẩu không chính xác')]"));
            for (WebElement element : elements) {
                System.out.println("Element found: " + element.getText());
            }
            System.out.println("SnackBar không hiển thị: " + e.getMessage());
            return false; // Không tìm thấy thông báo lỗi
        }
    }

    public boolean isLoginSuccessful() {
        try {
            // Kiểm tra một phần tử đặc trưng trên trang chính sau đăng nhập
            WebElement homeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.xpath("//android.view.View[@content-desc='Bệnh viện đa khoa A']")
            ));
            return homeElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginPageDisplayed() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return loginButton.isDisplayed();
    }

    public boolean isErrorMessage(){
        try {
            // Cần thêm thời gian chờ dài hơn để bắt toast
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement toast = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.widget.Toast[contains(@text, 'cần điền')]")
            ));
            System.out.println("Toast message: " + toast.getText());
            return true;
        } catch (Exception e) {
            System.out.println("Không bắt được Toast: " + e.getMessage());
            return false;
        }
    }

    // Phương thức đăng nhập chung
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        tapLoginButton();
    }
}