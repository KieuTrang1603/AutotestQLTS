package pagesapp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PageAppUtil;
import utils.PageUtil;

import java.time.Duration;

public class Main_tainReportPageApp {
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

    public Main_tainReportPageApp(AndroidDriver driver) {
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
