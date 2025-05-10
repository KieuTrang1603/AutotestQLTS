package pagesweb;

import base.BaseTestWeb;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class LoginPageWeb {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Định nghĩa các element trên trang đăng nhập
    @FindBy(name = "email")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath=("//p[text()='Trường này là bắt buộc']"))
    private WebElement errorMessage;

    // Constructor
    public LoginPageWeb(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Các hành động trên trang đăng nhập
    public void navigateToLoginPage() {
        driver.get(BaseTestWeb.LOGIN_URL);
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // kiem tra vi tri thong bao
    public boolean emptyusername(){
        int usernameBottomY = usernameInput.getLocation().getY() + usernameInput.getSize().getHeight();
        int errorMessageTopY = errorMessage.getLocation().getY();
        if(errorMessageTopY >= usernameBottomY){
            return true;
        }
        else
            return false;
    }

    // kiem tra vi tri thong bao
    public boolean emptypassword(){
        int usernameBottomY = passwordInput.getLocation().getY() + passwordInput.getSize().getHeight();
        int errorMessageTopY = errorMessage.getLocation().getY();
        if(errorMessageTopY >= usernameBottomY){
            return true;
        }
        else
            return false;
    }
    // Phương thức kết hợp để thực hiện đăng nhập
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginPageDisplayed() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return driver.getCurrentUrl().contains("signin");
    }

    //Kiem tra alert co hien thi khong
    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
    //lay noi dung text cua alert
    public String getAlertText() {
        try {
            return driver.switchTo().alert().getText();
        } catch (NoAlertPresentException e) {
            return "";
        }
    }
    //chap nhan alert
    public void acceptAlert() {
        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            // Không có alert để chấp nhận
        }
    }
    //doi alert xuat hien
    public boolean waitForAlert(int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Method to test SQL injection attempts
    public void attemptSqlInjection(String injectionString) {
        enterUsername(injectionString);
        enterPassword(injectionString);
        clickLoginButton();
    }

    // Method to test XSS vulnerabilities
    public void attemptXssAttack(String xssScript) {
        enterUsername(xssScript);
        enterPassword("123456");
        clickLoginButton();
    }
}