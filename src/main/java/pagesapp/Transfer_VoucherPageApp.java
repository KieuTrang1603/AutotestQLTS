package pagesapp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Transfer_VoucherPageApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait ;


    // Khởi tạo các phần tử giao diện bằng Page Factory
    public Transfer_VoucherPageApp(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public List<String> layDanhSachContentDesc() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> items = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//android.view.View[contains(@content-desc, 'Ngày chứng từ')]")
                )
        );
        List<String> data = new ArrayList<>();
        for (WebElement el : items) {
            data.add(el.getAttribute("content-desc"));
        }
        return data;
    }

    public void returnHomeApp(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement returnElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Button[@content-desc='Quay lại']"))
        );
        returnElement.click();
    }

    public void clickThemmoi(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement themMoiElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//android.view.View[@content-desc='Thêm']"))
        );
        themMoiElement.click();
    }
}
