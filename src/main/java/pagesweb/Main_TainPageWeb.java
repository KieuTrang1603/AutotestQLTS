package pagesweb;

import drivers.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main_TainPageWeb {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//button//span[text()='Tìm kiếm nâng cao']")
    private WebElement searchhigh;

    @FindBy(css=(".Toastify__toast--success"))
    private WebElement toastMessage;

    @FindBy(css=(".Toastify__toast-body"))
    private WebElement toastErrorMessage;
    // Constructor
    public Main_TainPageWeb(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    public void navigateToMain_TainPage(String user, String password){
        LoginPageWeb loginPageWeb = new LoginPageWeb(driver);
        HomePageWeb homePageWeb = new HomePageWeb(driver);
        loginPageWeb.navigateToLoginPage();
        loginPageWeb.login(user, password);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        homePageWeb.Main_tain_ui();
    }

    public void closeMenu(){
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();
    }

    public void BaoSuCo_Btn_click(){
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector(\"div[aria-hidden='true'][style*='z-index: -1']\")?.remove();"
        );

        // Bước 3: Đảm bảo allocation_btn đã sẵn sàng
        WebElement allocation_btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button//span[contains(text(), 'Báo sự cố')]")
                ));
        // Click bằng JS để đảm bảo không bị lớp nào cản
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", allocation_btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", allocation_btn);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getToastMessageText() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOf(toastMessage));
        return toast.getText();
    }

    public String getToastErrorMessageText() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOf(toastErrorMessage));
        return toast.getText();
    }

    public void disableTimKiemNangCao(){
        searchhigh.click();
    }
}
