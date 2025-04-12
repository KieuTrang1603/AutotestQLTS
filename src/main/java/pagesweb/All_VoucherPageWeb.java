package pagesweb;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class All_VoucherPageWeb {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Constructor
    public All_VoucherPageWeb(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateToAllocation_VoucherPage(){
        LoginPageWeb loginPageWeb = new LoginPageWeb(driver);
        HomePageWeb homePageWeb = new HomePageWeb(driver);
        loginPageWeb.navigateToLoginPage();
        loginPageWeb.login("pvt1", "123456");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        homePageWeb.Allocation_voucher_ui();
    }

    public void All_Btn_click(){
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector(\"div[aria-hidden='true'][style*='z-index: -1']\")?.remove();"
        );

        // Bước 3: Đảm bảo allocation_btn đã sẵn sàng
        WebElement allocation_btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button//span[contains(text(), 'Cấp phát TSCĐ')]")
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

}
