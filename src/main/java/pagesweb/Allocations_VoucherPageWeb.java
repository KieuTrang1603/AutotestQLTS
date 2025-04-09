package pagesweb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Allocations_VoucherPageWeb {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Constructor
    public Allocations_VoucherPageWeb(WebDriver driver) {
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
}
