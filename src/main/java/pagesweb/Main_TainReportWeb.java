package pagesweb;

import model.Asset;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.MyUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main_TainReportWeb {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(name = "arTgBaoCao")
    private WebElement ngayBaoCaoInput;

    @FindBy(name = "arTrangThai")
    private WebElement trangThai;
    @FindBy(css = "div[aria-labelledby='mui-component-select-arTrangThai']")
    private WebElement trangThaiStatus;

    @FindBy(xpath = "//div[contains(@class, 'MuiDialog-container') and contains(@class, 'MuiDialog-scrollPaper')]//label[.//text()[contains(.,'Phòng ban báo sự cố')]]/following::input[1]")
    private WebElement phongBaoSuCoInput;

    @FindBy(xpath = "//div[contains(@class, 'MuiDialog-container') and contains(@class, 'MuiDialog-scrollPaper')]//label[.//text()[contains(.,'Phòng tiếp nhận')]]/following::input[1]")
    private WebElement phongTiepNhanInput;

    @FindBy(xpath = "//div[contains(@class, 'MuiDialog-container') and contains(@class, 'MuiDialog-scrollPaper')]//label[.//text()[contains(.,'Người báo cáo')]]/following::input[1]")
    private WebElement nguoiBaoSuCoInput;

    @FindBy(name = "code")
    private WebElement maTaiSan;
    @FindBy(name = "managementCode")
    private WebElement maQuanLy;
    @FindBy(name = "name")
    private WebElement tenTaiSan;
    @FindBy(name = "model")
    private WebElement model;
    @FindBy(name = "serialNumber")
    private WebElement serial;
    @FindBy(name = "managementDepartment")
    private WebElement phongQuanLy;

    @FindBy(xpath = "//button//span[text()='Chọn tài sản']")
    private WebElement chonts;

    @FindBy(id = "upload-images")
    private WebElement uploadFile;
    @FindBy(xpath=("//p[text()='Trường này là bắt buộc']"))
    private WebElement errorMessage;
    @FindBy(xpath=("//p[text()='Ngày báo cáo không được lớn hơn ngày hôm nay']"))
    private WebElement errorMessage1;
    // Constructor
    public Main_TainReportWeb(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getNgayBaoCao() {
        return ngayBaoCaoInput.getAttribute("value");
    }

    public boolean isNgayBaoCaoEditable() {
        return ngayBaoCaoInput.isEnabled();
    }

    public void clearNgayBaoCao(){
        ngayBaoCaoInput.click();
        // Nếu readonly, xoá bằng JS
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", ngayBaoCaoInput);
        ngayBaoCaoInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        ngayBaoCaoInput.sendKeys(Keys.DELETE);
    }

    public void setNgayBaoCaoInput(String date){
        clearNgayBaoCao();
        // Nhập ngày mới
        ngayBaoCaoInput.sendKeys(date);
    }

    public boolean isTrangThaiReadonly() {
        return trangThai.isEnabled();
    }

    public String getTrangThaiStatus(){
        return trangThaiStatus.getText().trim();
    }

    public void openDropdownPhongBaoSuCo() {
        phongBaoSuCoInput.click();
    }

    public void clearPhongBaoSuCo(){
        openDropdownPhongBaoSuCo();
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", phongBaoSuCoInput);
        phongBaoSuCoInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        phongBaoSuCoInput.sendKeys(Keys.DELETE);
    }

    public List<WebElement> getAllElementOptionsPhongBaoSuCo() {
        openDropdownPhongBaoSuCo();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Đợi dropdown hiển thị (thường là thẻ ul > li với class Autocomplete-option)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("asynchronous-demo-popup")
        ));

        // Lấy toàn bộ li
        List<WebElement> optionElements = driver.findElements(By.cssSelector("#asynchronous-demo-popup li"));
        return optionElements;
    }

    public List<String> getAllDropdownOptionsPhongBaoSuCo() {
        List<String> options = new ArrayList<>();
        for (WebElement option : getAllElementOptionsPhongBaoSuCo()) {
            options.add(option.getText());
        }
        return options;
    }

    public void chonPhongBaoSuCoInput(){
        getAllElementOptionsPhongBaoSuCo().get(0).click();
    }

    public String getPhongBaoSuCoInput() {
        return phongBaoSuCoInput.getAttribute("value");
    }

    public boolean checkPhongBaoSuCo(List<String> data){
        List<String> data1 = MyUtil.normalizeList(data);
        List<String> data2 = MyUtil.normalizeList(getAllDropdownOptionsPhongBaoSuCo());
        System.out.println("Danh sách theo UI: " + data2);
        if(data1.equals(data2)){
            return true;
        }
        else
            return false;
    }

    public void openDropdownPhongTiepNhan() {
        phongTiepNhanInput.click();
    }

    public void clearPhongTiepNhan(){
        openDropdownPhongTiepNhan();
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", phongTiepNhanInput);
        phongTiepNhanInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        phongTiepNhanInput.sendKeys(Keys.DELETE);
    }

    public List<WebElement> getAllElementOptionsPhongTiepNhan() {
        openDropdownPhongTiepNhan();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Đợi dropdown hiển thị (thường là thẻ ul > li với class Autocomplete-option)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("asynchronous-demo-popup")
        ));

        // Lấy toàn bộ li
        List<WebElement> optionElements = driver.findElements(By.cssSelector("#asynchronous-demo-popup li"));
        return optionElements;
    }

    public List<String> getAllDropdownOptionsPhongTiepNhan() {
        List<String> options = new ArrayList<>();
        for (WebElement option : getAllElementOptionsPhongTiepNhan()) {
            options.add(option.getText());
        }
        return options;
    }

    public void chonPhongTiepNhanInput(){
        getAllElementOptionsPhongTiepNhan().get(0).click();
    }

    public boolean checkPhongTiepNhan(List<String> data){
        List<String> data1 = MyUtil.normalizeList(data);
        List<String> data2 = MyUtil.normalizeList(getAllDropdownOptionsPhongTiepNhan());
        System.out.println("Danh sách theo UI: " + data2);
        if(data1.equals(data2)){
            return true;
        }
        else
            return false;
    }

    public boolean isNguoiBaoSuCoAble() {
        clearPhongBaoSuCo();
        return nguoiBaoSuCoInput.isEnabled();
    }

    public void openDropdownNguoiBaoSuCo() {
        nguoiBaoSuCoInput.click();
    }

    public void clearNguoiBaoSuCo(){
        openDropdownNguoiBaoSuCo();
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", nguoiBaoSuCoInput);

        // Xoá nội dung bằng tổ hợp phím
//        ngayChungTuInput.sendKeys(Keys.chord(Keys.CONTROL, date), Keys.BACK_SPACE);
        nguoiBaoSuCoInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        nguoiBaoSuCoInput.sendKeys(Keys.DELETE);
    }

    public List<WebElement> getAllElementOptionsNguoiBaoSuCo() {
        openDropdownNguoiBaoSuCo();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Đợi dropdown hiển thị (thường là thẻ ul > li với class Autocomplete-option)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("asynchronous-demo-popup")
        ));

        // Lấy toàn bộ li
        List<WebElement> optionElements = driver.findElements(By.cssSelector("#asynchronous-demo-popup li"));
        return optionElements;
    }

    public List<String> getAllDropdownOptionsNguoiBaoSuCo() {
        List<String> options = new ArrayList<>();
        for (WebElement option : getAllElementOptionsNguoiBaoSuCo()) {
            options.add(option.getText());
        }
        return options;
    }

    public void chonNguoiBaoSuCoInput(){
        getAllElementOptionsNguoiBaoSuCo().get(1).click();
    }

    public boolean checkNguoiBaoSuCo(List<String> data){
        List<String> data1 = MyUtil.normalizeList(data);
        List<String> data2 = MyUtil.normalizeList(getAllDropdownOptionsNguoiBaoSuCo());
        if(data1.equals(data2)){
            return true;
        }
        else
            return false;
    }

    public boolean isChonTaiSanAble() {
        clearPhongBaoSuCo();
        return chonts.isEnabled();
    }

    public void openDialogTS(){
        chonts.click();
    }

    public String getMaTaiSan() {
        return maTaiSan.getAttribute("value");
    }
    public boolean checkDataTaiSan(Asset as){
        Asset assetUI = new Asset();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assetUI.setCode(maTaiSan.getAttribute("value"));
        assetUI.setManagement_code(maQuanLy.getAttribute("value"));
        assetUI.setName(tenTaiSan.getAttribute("value"));
        assetUI.setModel(model.getAttribute("value"));
        assetUI.setSerial(serial.getAttribute("value"));
        assetUI.setManager_department(phongQuanLy.getAttribute("value"));
        System.out.println("Tài sản trên UI: " +assetUI.hienThiTaisan());
        System.out.println("Tài sản trên DB: " +as.hienThiTaisan());
        if(assetUI.getCode().equals(as.getCode())
                && assetUI.getManagement_code().equals(as.getManagement_code())
                && assetUI.getName().equals(as.getName())
                && assetUI.getModel().equals(as.getModel())
                && assetUI.getSerial().equals(as.getSerial())
                && assetUI.getManager_department().equals(as.getManager_department())){
            return true;
        }
        else return false;
    }

    public void setUploadFile(String fileName){
        String fullPath = MyUtil.DOWNLOAD_PATH + "\\" + fileName;
        uploadFile.sendKeys(fullPath);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUpload2File(String fileName1, String fileName2){
        String fullPath = MyUtil.DOWNLOAD_PATH + "\\" + fileName1 + "\n" + MyUtil.DOWNLOAD_PATH + "\\" + fileName2;
        uploadFile.sendKeys(fullPath);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUpLoadFile(String fileName){
        boolean isUploadVisible = driver.findElements(By.cssSelector("img[src*='" + fileName + "']")).size() > 0;
        System.out.println("File được tải lên visible: " + isUploadVisible);
        return isUploadVisible;
    }

    public boolean isUpLoad2File(String fileName1, String fileName2){
        boolean isFile1Visible = driver.findElements(By.cssSelector("img[src*='" + fileName1 + "']")).size() > 0;
        boolean isFile2Visible = driver.findElements(By.cssSelector("img[src*='" + fileName2 + "']")).size() > 0;
        boolean isUploadVisible = isFile1Visible && isFile2Visible;;
        System.out.println("File được tải lên visible: " + isUploadVisible);
        return isUploadVisible;
    }

    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed1() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage1));
            return errorMessage1.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean empty(int a){
        boolean result =false;
        switch (a){
            case 1:
                int usernameBottomY1 = ngayBaoCaoInput.getLocation().getY() + ngayBaoCaoInput.getSize().getHeight();
                int errorMessageTopY1 = errorMessage.getLocation().getY();
                if(errorMessageTopY1 >= usernameBottomY1){
                    result= true;
                }
                break;
            case 2:
                int usernameBottomY2 = phongBaoSuCoInput.getLocation().getY() + phongBaoSuCoInput.getSize().getHeight();
                int errorMessageTopY2 = errorMessage.getLocation().getY();
                if(errorMessageTopY2 >= usernameBottomY2){
                    result= true;
                }
                break;
            case 3:
                int usernameBottomY3 = phongTiepNhanInput.getLocation().getY() + phongTiepNhanInput.getSize().getHeight();
                int errorMessageTopY3 = errorMessage.getLocation().getY();
                if(errorMessageTopY3 >= usernameBottomY3){
                    result= true;
                }
                break;
            case 4:
                int usernameBottomY4 = nguoiBaoSuCoInput.getLocation().getY() + nguoiBaoSuCoInput.getSize().getHeight();
                int errorMessageTopY4 = errorMessage.getLocation().getY();
                if(errorMessageTopY4 >= usernameBottomY4){
                    result= true;
                }
                break;
            case 5:
                int usernameBottomY7 = ngayBaoCaoInput.getLocation().getY() + ngayBaoCaoInput.getSize().getHeight();
                int errorMessageTopY7 = errorMessage1.getLocation().getY();
                if(errorMessageTopY7 >= usernameBottomY7){
                    result= true;
                }
                break;
        }
        return result;
    }
    public void setLuu_btn(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement luu_btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button//span[text()='Lưu']")));
        luu_btn.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
