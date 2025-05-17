package pagesapp;

import base.BaseApp;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PageAppUtil;
import utils.PageUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Main_TainReportPageApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait ;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Thông tin sửa chữa')]")
    private WebElement thongTinSC_label;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Trạng thái')]/following-sibling::android.view.View[1]")
    private WebElement ngayBaoCaoInput;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Trạng thái')]/following-sibling::android.view.View[2]")
    private WebElement trangThai;

    @FindBy(xpath = "//android.widget.ScrollView/android.widget.Button[1]/android.view.View[1]/android.view.View")
    private WebElement phongBanBaoSuCoInput;
    @FindBy(xpath = "//android.widget.ScrollView/android.widget.Button[1]")
    private WebElement phongBanBaoSuCoButton;

    @FindBy(xpath = "//android.widget.ScrollView/android.widget.Button[2]")
    private WebElement phongBanTiepNhanInput;
    public Main_TainReportPageApp(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getNgayBaoCaoValue() {
        return PageUtil.getValueText(ngayBaoCaoInput);
    }

    public boolean isNgayBaoCaoEdit(){
        ngayBaoCaoInput.click();
        boolean isDatePickerVisible = driver.findElements(By.xpath("//android.view.View[contains(@content-desc, 'Chọn ngày')]")).size() > 0;
        System.out.println("Date Picker visible: " + isDatePickerVisible);
        return isDatePickerVisible;
    }

    public boolean isTrangThaiAble(){
        return PageAppUtil.isEdit(trangThai);
    }

    public boolean isPhongBanBaoSuCoAble(){
        return PageAppUtil.isEdit(phongBanBaoSuCoButton);
    }
    public String getPhongBanBaoSuCo() {
        return PageAppUtil.getValueContent(phongBanBaoSuCoInput);
    }

    public boolean checkPhongBanBaoSuCo(String data){
        if(getPhongBanBaoSuCo().contains(data)){
            return true;
        }
        else
            return false;
    }

    public void openDropdownPhongBanTiepNhan() {
        PageUtil.clickElement(phongBanTiepNhanInput);
    }

    public List<String> getAllContentDescPhongTiepNhan() {
        openDropdownPhongBanTiepNhan();
        List<WebElement> scrollViews;
        WebElement scrollView = null;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        scrollViews = driver.findElements(By.xpath("//android.widget.ScrollView"));
        if(!scrollViews.isEmpty()) {
            scrollView = scrollViews.get(0);
        }else {
            scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.view.View")
            ));
        }

        Set<String> contentDescs = new LinkedHashSet<>();
        int scrollCount = 0;
        int maxScroll = 100;

        while (scrollCount < maxScroll) {
            List<WebElement> visibleElements = scrollView.findElements(By.xpath(".//android.view.View[@content-desc]"));
            int before = contentDescs.size();

            for (WebElement el : visibleElements) {
                contentDescs.add(el.getAttribute("content-desc"));
            }

            if (contentDescs.size() == before) {
                break;
            }

            BaseApp.scrollDown(scrollView);
            scrollCount++;
        }

        return new ArrayList<>(contentDescs);
    }

    public List<WebElement> getAllElementOptionsPhongBanTiepNhan() {
        openDropdownPhongBanTiepNhan();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.view.View")
        ));
        List<WebElement> elements = scrollView.findElements(By.xpath(".//android.view.View[@content-desc]"));
        return elements;
    }

    public boolean checkPhongBanTiepNhan(List<String> data){
        List<String> danhsach = getAllContentDescPhongTiepNhan();
        System.out.println("DB: " + data.size());
        System.out.println("APP: " + danhsach.size());
        if(data.size() == (danhsach.size())){
            return true;
        }
        else
            return false;
    }

    public String getPhongBanTiepNhanInput() {
        WebElement element = phongBanTiepNhanInput.findElement(By.xpath(".//android.view.View[@content-desc]"));
        return element.getAttribute("content-desc");
    }

    public void chonPhongBanTiepNhanInput(){
        getAllElementOptionsPhongBanTiepNhan().get(1).click();
    }
    public boolean isMainTainDialogDisplayed() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            return thongTinSC_label.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
