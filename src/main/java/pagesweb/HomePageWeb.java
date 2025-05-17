package pagesweb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePageWeb {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//div[contains(@class, 'topbar-navigation')]//button//span[@class='item-text']")
    private List<WebElement> menuButtons;

    @FindBy(xpath = "//button//span[text()='Quản lý']")
    private WebElement manager;

    @FindBy(xpath = "//button//span//div//span[text()='Quản lý TSCĐ']")
    private WebElement manager_tscd;

    @FindBy(xpath = "//button[text()='Cấp phát TSCĐ']")
    private WebElement allocation_voucher_tscd;

    @FindBy(xpath = "//button[text()='Điều chuyển TSCĐ']")
    private WebElement transfer_voucher_tscd;

    @FindBy(xpath = "//button[text()='Sửa chữa - thay thế']")
    private WebElement main_tain_tscd;

    @FindBy(xpath = "//button[text()='Danh sách TSCĐ']")
    private WebElement assets_tscd;

    // Constructor
    public HomePageWeb(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    //Kiem tra thanh menu hien thi dung k
    public boolean isMenuDisplayedCorrectly(List <String> expectedMenus){
        if (menuButtons.size() != expectedMenus.size()) {
            System.out.println("Số lượng menu không khớp! Mong đợi: " + expectedMenus.size() + ", Thực tế: " + menuButtons.size());
            return false;
        }

        // Kiểm tra từng menu có tồn tại không
        for (String expectedMenu : expectedMenus) {
            boolean menuExists = menuButtons.stream().anyMatch(button -> button.getText().trim().equalsIgnoreCase(expectedMenu));
            if (!menuExists) {
                System.out.println("Không tìm thấy menu: " + expectedMenu);
                return false;
            }
        }
        return true;
    }

    public void Allocation_voucher_ui(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        manager.click();
        manager_tscd.click();
        allocation_voucher_tscd.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void Transfer_voucher_ui(){
        manager.click();
        manager_tscd.click();
        transfer_voucher_tscd.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void Main_tain_ui(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement manager = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button//span[text()='Quản lý']")
        ));
        manager.click();
        manager_tscd.click();
        main_tain_tscd.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void Assets_ui(){
        manager.click();
        manager_tscd.click();
        assets_tscd.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
