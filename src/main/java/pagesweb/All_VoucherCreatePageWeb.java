package pagesweb;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.MyUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class All_VoucherCreatePageWeb {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // Constructor
    @FindBy(id = "mui-pickers-date")
    private WebElement ngayTaoPhieuInput;

    @FindBy(xpath = "//label[.//text()[contains(.,'Ngày chứng từ')]]/following::input[1]")
    private WebElement ngayChungTuInput;

    @FindBy(css = "input.MuiInputBase-input.MuiInput-input")
    private WebElement phongBanBanGiaoInput;

    @FindBy(xpath = "//label[.//text()[contains(.,'Người bàn giao')]]/following::input[1]")
    private WebElement nguoiBanGiaoInput;

    public All_VoucherCreatePageWeb(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getNgayTaoPhieuValue() {
        return ngayTaoPhieuInput.getAttribute("value");
    }

    public boolean isNgayTaoPhieuReadonly() {
        return ngayTaoPhieuInput.getAttribute("readonly") != null;
    }

    public String getNgayChungTu() {
        return ngayChungTuInput.getAttribute("value");
    }

    public void setNgayChungTuInput(String date){
        ngayChungTuInput.click();
//        ngayChungTuInput.clear();
        ngayChungTuInput.sendKeys(date);
    }

    public boolean isNgayChungTuEditable() {
        return ngayChungTuInput.isEnabled();
    }

    public boolean isNgayChungTuKhongHopLe() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String isInvalid = ngayChungTuInput.getAttribute("aria-invalid");
        return isInvalid != null && isInvalid.equals("true");
    }

    public String getPhongBanBanGiao() {
        return phongBanBanGiaoInput.getAttribute("value");
    }

    public boolean checkPhongBanBanGiao(String data){
        if(data == getPhongBanBanGiao()){
            return true;
        }
        else
            return false;
    }

    public void openDropdown() {
        nguoiBanGiaoInput.click();
    }

    public List<String> getAllDropdownOptions() {
        openDropdown();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Đợi dropdown hiển thị (thường là thẻ ul > li với class Autocomplete-option)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("asynchronous-demo-popup")
        ));

        // Lấy toàn bộ li
        List<WebElement> optionElements = driver.findElements(By.cssSelector("#asynchronous-demo-popup li"));

        List<String> options = new ArrayList<>();
        for (WebElement option : optionElements) {
            options.add(option.getText());
        }
        return options;
    }

    public boolean checkNguoiBanGiao(List<String> data){
        List<String> data1 = MyUtil.normalizeList(data);
        List<String> data2 = MyUtil.normalizeList(getAllDropdownOptions());
        if(data1.equals(data2)){
            return true;
        }
        else
            return false;
    }
}
