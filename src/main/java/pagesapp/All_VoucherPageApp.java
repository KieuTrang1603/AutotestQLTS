package pagesapp;

import helpers.AllocationHelper;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import model.Allocation;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class All_VoucherPageApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait ;

    Allocation allocation;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Cấp phát')]")
    private WebElement CP_label;

    // Khởi tạo các phần tử giao diện bằng Page Factory
    public All_VoucherPageApp(AndroidDriver driver) {
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

    public void searchAsset(String ma){
        WebElement inputSearch = driver.findElement(By.xpath("//android.widget.EditText")); // hoặc sửa thành id thực tế nếu khác
        inputSearch.click();
        inputSearch.clear();
        inputSearch.sendKeys(ma);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchHigh(int a){
        WebElement searchhigh = driver.findElement(By.xpath("//android.widget.EditText/android.view.View[2]"));
        searchhigh.click();
        WebElement trangThai = driver.findElement(By.xpath("//android.widget.Button[@content-desc='Trạng thái']"));
        trangThai.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.view.View")));
        List<WebElement> trangThaiItems = popup.findElements(By.xpath(".//android.view.View[@content-desc]"));
        switch (a){
            case 1:
                trangThaiItems.get(0).click();
                break;
            case 2:
                trangThaiItems.get(3).click();
                break;
            case 3:
                trangThaiItems.get(1).click();
                break;
        }
        WebElement xacNhanButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@content-desc='Xác nhận']")));
        xacNhanButton.click();
    }

    public Allocation getDuLieuCP(){
        allocation = AllocationHelper.getAllocationMobile(layDanhSachContentDesc()).get(0);
        return allocation;
    }
    public boolean checkBanghiCapphat(String ma, int a, String PBBG, String PBTN){
        searchAsset(ma);
        searchHigh(a);
        getDuLieuCP();
        boolean check = false;
        if(allocation.getPhongGiao().contains(PBBG) && allocation.getPhongNhan().contains(PBTN)){
            check =true;
            return check;
        } else
            return check;
    }

    public boolean isAllocatonDialogDisplayed() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            return CP_label.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
