package pagesweb;

import Dialogs.DS_TSCD_Dialog;
import drivers.DriverManager;
import org.openqa.selenium.*;
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

    @FindBy(xpath = "//label[.//text()[contains(.,'Trạng thái phiếu')]]/following::input[1]")
    private WebElement trangThaiPhieuInput;

    @FindBy(xpath = "//label[.//span[text()='Phòng ban tiếp nhận']]/following-sibling::div//input")
    private WebElement phongBanTiepNhanInput;

    @FindBy(xpath = "//label[.//text()[contains(.,'Người tiếp nhận')]]/following::input[1]")
    private WebElement nguoiTiepNhanInput;

    @FindBy(xpath = "//button//span[text()='Chọn tài sản cấp phát']")
    private WebElement chonts;
    String soluong;

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
        // Nếu readonly, xoá bằng JS
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", ngayChungTuInput);

        // Xoá nội dung bằng tổ hợp phím
        ngayChungTuInput.sendKeys(Keys.chord(Keys.CONTROL, date), Keys.BACK_SPACE);
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

    public void openDropdownNguoiBanGiao() {
        nguoiBanGiaoInput.click();
    }

    public List<String> getAllDropdownOptionsNguoiBanGiao() {
        openDropdownNguoiBanGiao();
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
        List<String> data2 = MyUtil.normalizeList(getAllDropdownOptionsNguoiBanGiao());
        if(data1.equals(data2)){
            return true;
        }
        else
            return false;
    }

    public void openDropdownTrangThaiPhieu(){
        trangThaiPhieuInput.click();
    }

    public List<String> getAllDropdownOptionsTrangThaiPhieu() {
        openDropdownTrangThaiPhieu();
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

    public boolean checkTrangThaiPhieu(List<String> data){
        List<String> data1 = MyUtil.normalizeList(data);
        List<String> data2 = MyUtil.normalizeList(getAllDropdownOptionsTrangThaiPhieu());
        if(data1.equals(data2)){
            return true;
        }
        else
            return false;
    }

    public void openDropdownPhongBanTiepNhan() {
        phongBanTiepNhanInput.click();
    }

    public String getPhongBanTiepNhanInput() {
        return phongBanTiepNhanInput.getAttribute("value");
    }

    public List<WebElement> getAllElementOptionsPhongBanTiepNhan() {
        openDropdownPhongBanTiepNhan();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Đợi dropdown hiển thị (thường là thẻ ul > li với class Autocomplete-option)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("asynchronous-demo-popup")
        ));

        // Lấy toàn bộ li
        List<WebElement> optionElements = driver.findElements(By.cssSelector("#asynchronous-demo-popup li"));
        return optionElements;
    }

    public List<String> getAllDropdownOptionsPhongBanTiepNhan() {
        List<String> options = new ArrayList<>();
        for (WebElement option : getAllElementOptionsPhongBanTiepNhan()) {
            options.add(option.getText());
        }
        return options;
    }

    public void chonPhongBanTiepNhanInput(){
        getAllElementOptionsPhongBanTiepNhan().get(1).click();
    }

    public boolean checkPhongBanTiepNhan(List<String> data){
//        List<String> data1 = MyUtil.normalizeList(data);
//        List<String> data2 = MyUtil.normalizeList(getAllDropdownOptionsPhongBanTiepNhan());
//        if(data1.equals(data2)){
//            return true;
//        }
//        else
//            return false;
        if(data.size() == (getAllDropdownOptionsPhongBanTiepNhan().size())){
            return true;
        }
        else
            return false;
    }

    public boolean isNguoiTiepNhanAble() {
        phongBanTiepNhanInput.clear();
        return nguoiTiepNhanInput.isEnabled();
    }

    public void openDropdownNguoiTiepNhan() {
        nguoiTiepNhanInput.click();
    }

    public List<WebElement> getAllElementOptionsNguoiTiepNhan() {
        openDropdownNguoiTiepNhan();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Đợi dropdown hiển thị (thường là thẻ ul > li với class Autocomplete-option)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("asynchronous-demo-popup")
        ));

        // Lấy toàn bộ li
        List<WebElement> optionElements = driver.findElements(By.cssSelector("#asynchronous-demo-popup li"));
        return optionElements;
    }

    public List<String> getAllDropdownOptionsNguoiTiepNhan() {
        List<String> options = new ArrayList<>();
        for (WebElement option : getAllElementOptionsNguoiTiepNhan()) {
            options.add(option.getText());
        }
        return options;
    }

    public boolean checkNguoiTiepNhan(List<String> data){
        List<String> data1 = MyUtil.normalizeList(data);
        List<String> data2 = MyUtil.normalizeList(getAllDropdownOptionsNguoiTiepNhan());
        if(data1.equals(data2)){
            return true;
        }
        else
            return false;
    }

    public void openDialogChonTS(){
//        setNgayChungTuInput(MyUtil.getNgaychungtu());
        chonts.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement activeDialog = wait.until(driver -> {
            List<WebElement> dialogs = driver.findElements(By.className("MuiDialog-root"));
            for (WebElement dialog : dialogs) {
                String ariaHidden = dialog.getAttribute("aria-hidden");
                if (ariaHidden == null) {
                    return dialog; // Đây là dialog đang hiển thị
                }
            }
            return null;
        });
        // 3. Tìm phần tử phân trang trong dialog đang mở
        WebElement paginationTextElement = activeDialog.findElement(
                By.xpath("//div[contains(@class, 'MuiDialogContent-root')]//p[contains(@class, 'MuiTablePagination-caption') and contains(text(), 'trong')]")
        );
        String paginationText = paginationTextElement.getText();
        System.out.println("Text lấy được: " + paginationText);
        soluong=paginationText;
    }


    public boolean checkSobanghi(Integer sobanghi){
//        DS_TSCD_Dialog ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        System.out.println("Dữ liệu lấy ra: " + soluong);
        String total = MyUtil.getSobangi(soluong);
        System.out.println("Số lương bản ghi trên UI: " + total);
        Integer sobanghi_ui= MyUtil.convertToInt(total);
        if (sobanghi_ui != null) {
            System.out.printf("Số lượng bản ghi sau khi convert: %d%n", sobanghi_ui);
        } else {
            System.out.println("Số lượng bản ghi sau khi convert: null");
        }
        if(sobanghi_ui != null && sobanghi_ui.equals(sobanghi)){
            return true;}
        else
            return false;
    }


}
