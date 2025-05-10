package pagesapp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class HomePageApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait ;

    @FindBy(xpath = "//android.view.View[@content-desc='Chức năng']/following-sibling::android.view.View//android.view.View[@content-desc]")
    private List<WebElement> menuItems;

    @FindBy(xpath = "//android.view.View[contains(@content-desc,'Cấp phát')]")
    private WebElement all_voucher;

    @FindBy(xpath = "//android.view.View[contains(@content-desc,'Điều chuyển')]")
    private WebElement transf_voucher;

    @FindBy(xpath = "//android.view.View[contains(@content-desc,'Sửa chữa')]")
    private WebElement main_tain;

//    @FindBy(xpath = "//android.widget.ScrollView/android.view.View[3]")
//    private WebElement scanQR;
    //máy thật
    @FindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View[3]")
    private WebElement scanQR;

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

        // Kiểm tra từng menu mong đợi có tồn tại (phần tên) trong danh sách hiển thị không
        for (String expectedMenu : expectedMenus) {
            boolean menuExists = false;
            for (WebElement menuItem : menuItems) {
                String actualText = menuItem.getAttribute("content-desc");
                System.out.println(actualText);
                if (actualText.contains(expectedMenu)) { // Kiểm tra xem text thực tế có bắt đầu bằng tên menu mong đợi không
                    menuExists = true;
                    break;
                }
            }
            if (!menuExists) {
                System.out.println("Không tìm thấy menu có tên: " + expectedMenu);
                return false;
            }
        }
        return true;
    }

    public void setLogOutAM(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logOutElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.ScrollView/android.view.View[4]"))
        );

        //máy thật
//        WebElement logOutElement = wait.until(
//                ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View[4]"))
//        );
        logOutElement.click();
        LogOutPageApp logOutPageApp = new LogOutPageApp(driver);
        logOutPageApp.tapLogOutView();
    }
    public void setLogOutAU(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logOutElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View[4]"))
        );
        logOutElement.click();
        LogOutPageApp logOutPageApp = new LogOutPageApp(driver);
        logOutPageApp.tapLogOutView();
    }

    public void setLogOutORG(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logOutElement = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.ScrollView/android.view.View[3]"))
        );
        logOutElement.click();
        LogOutPageApp logOutPageApp = new LogOutPageApp(driver);
        logOutPageApp.tapLogOutView();
    }

    public void setLogOut(int a){
        switch (a){
            case 0:
                setLogOutORG();
                break;
            case 1:
                setLogOutAM();
                break;
            case 2,3:
                setLogOutAU();
                break;
        }
    }

    public void navigationtoAllocation(){
        all_voucher.click();
    }

    public void navigationtoTransfer(){
        transf_voucher.click();
    }

    public void navigationtoScanQR(){
        scanQR.click();
    }

    public void navigationtoMaintain(){
        main_tain.click();
    }
}
