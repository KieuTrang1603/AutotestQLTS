package pagesweb;

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
    String soluong;
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

//    @FindBy(xpath = "//button//span[text()='Lưu']")
//    private WebElement luu_btn;

    @FindBy(xpath=("//p[text()='Trường này là bắt buộc']"))
    private WebElement errorMessage;
    @FindBy(xpath=("//p[text()='Không được lớn hơn ngày hiện tại']"))
    private WebElement errorMessage1;
    @FindBy(css=(".Toastify__toast-body"))
    private WebElement toastMessage;
    @FindBy(id=("parentAssetAllocation"))
    private WebElement parentAssetForm;

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

    public void clearNgayChungTu(){
        ngayChungTuInput.click();
        // Nếu readonly, xoá bằng JS
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", ngayChungTuInput);

        // Xoá nội dung bằng tổ hợp phím
//        ngayChungTuInput.sendKeys(Keys.chord(Keys.CONTROL, date), Keys.BACK_SPACE);
        ngayChungTuInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        ngayChungTuInput.sendKeys(Keys.DELETE);
    }

    public void setNgayChungTuInput(String date){
        clearNgayChungTu();
        // Nhập ngày mới
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
        if(data.equals(getPhongBanBanGiao())){
            return true;
        }
        else
            return false;
    }

    public void openDropdownNguoiBanGiao() {
        nguoiBanGiaoInput.click();
    }

    public void clearNguoiBanGiao(){
        openDropdownNguoiBanGiao();
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", nguoiBanGiaoInput);

        // Xoá nội dung bằng tổ hợp phím
//        ngayChungTuInput.sendKeys(Keys.chord(Keys.CONTROL, date), Keys.BACK_SPACE);
        nguoiBanGiaoInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        nguoiBanGiaoInput.sendKeys(Keys.DELETE);
    }

    public List<WebElement> getAllElementOptionsNguoiBanGiao() {
        openDropdownNguoiBanGiao();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Đợi dropdown hiển thị (thường là thẻ ul > li với class Autocomplete-option)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("asynchronous-demo-popup")
        ));

        // Lấy toàn bộ li
        List<WebElement> optionElements = driver.findElements(By.cssSelector("#asynchronous-demo-popup li"));
        return optionElements;
    }

    public List<String> getAllDropdownOptionsNguoiBanGiao() {
        List<String> options = new ArrayList<>();
        for (WebElement option : getAllElementOptionsNguoiBanGiao()) {
            options.add(option.getText());
        }
        return options;
    }

    public void chonNguoiBanGiaoInput(){
        getAllElementOptionsNguoiBanGiao().get(1).click();
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        trangThaiPhieuInput.click();
    }

    public void clearTrangThaiPhieu(){
        openDropdownTrangThaiPhieu();
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", trangThaiPhieuInput);

        // Xoá nội dung bằng tổ hợp phím
//        ngayChungTuInput.sendKeys(Keys.chord(Keys.CONTROL, date), Keys.BACK_SPACE);
        trangThaiPhieuInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        trangThaiPhieuInput.sendKeys(Keys.DELETE);
    }

    public List<WebElement> getAllElementOptionsTrangThaiPhieu() {
        openDropdownTrangThaiPhieu();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Đợi dropdown hiển thị (thường là thẻ ul > li với class Autocomplete-option)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("asynchronous-demo-popup")
        ));

        // Lấy toàn bộ li
        List<WebElement> optionElements = driver.findElements(By.cssSelector("#asynchronous-demo-popup li"));
        return optionElements;
    }

    public List<String> getAllDropdownOptionsTrangThaiPhieu() {
        List<String> options = new ArrayList<>();
        for (WebElement option : getAllElementOptionsTrangThaiPhieu()) {
            options.add(option.getText());
        }
        return options;
    }

    public void chonTrangThaiPhieu(int a){
        switch (a){
            case 1:
                getAllElementOptionsTrangThaiPhieu().get(0).click();
                break;
            case 2:
                getAllElementOptionsTrangThaiPhieu().get(1).click();
                break;
            case 3:
                getAllElementOptionsTrangThaiPhieu().get(2).click();
                break;
        }
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

    public void clearPhongBanTiepNhan(){
        openDropdownPhongBanTiepNhan();
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", phongBanTiepNhanInput);

        // Xoá nội dung bằng tổ hợp phím
//        ngayChungTuInput.sendKeys(Keys.chord(Keys.CONTROL, date), Keys.BACK_SPACE);
        phongBanTiepNhanInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        phongBanTiepNhanInput.sendKeys(Keys.DELETE);
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

    public void clearNguoiTiepNhan(){
        openDropdownNguoiTiepNhan();
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", nguoiTiepNhanInput);

        // Xoá nội dung bằng tổ hợp phím
//        ngayChungTuInput.sendKeys(Keys.chord(Keys.CONTROL, date), Keys.BACK_SPACE);
        nguoiTiepNhanInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        nguoiTiepNhanInput.sendKeys(Keys.DELETE);
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

    public void chonNguoiTiepNhanInput(){
        getAllElementOptionsNguoiTiepNhan().get(1).click();
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

    public void openDialogTS(){
        chonts.click();
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

    public String getToastMessageText() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return toastMessage.getText().trim();
    }

    public boolean empty(int a){
        boolean result =false;
        switch (a){
            case 1:
                int usernameBottomY1 = ngayChungTuInput.getLocation().getY() + ngayChungTuInput.getSize().getHeight();
                int errorMessageTopY1 = errorMessage.getLocation().getY();
                if(errorMessageTopY1 >= usernameBottomY1){
                    result= true;
                }
                break;
            case 2:
                int usernameBottomY2 = phongBanBanGiaoInput.getLocation().getY() + phongBanBanGiaoInput.getSize().getHeight();
                int errorMessageTopY2 = errorMessage.getLocation().getY();
                if(errorMessageTopY2 >= usernameBottomY2){
                    result= true;
                }
                break;
            case 3:
                int usernameBottomY3 = nguoiBanGiaoInput.getLocation().getY() + nguoiBanGiaoInput.getSize().getHeight();
                int errorMessageTopY3 = errorMessage.getLocation().getY();
                if(errorMessageTopY3 >= usernameBottomY3){
                    result= true;
                }
                break;
            case 4:
                int usernameBottomY4 = trangThaiPhieuInput.getLocation().getY() + trangThaiPhieuInput.getSize().getHeight();
                int errorMessageTopY4 = errorMessage.getLocation().getY();
                if(errorMessageTopY4 >= usernameBottomY4){
                    result= true;
                }
                break;
            case 5:
                int usernameBottomY5 = phongBanTiepNhanInput.getLocation().getY() + phongBanTiepNhanInput.getSize().getHeight();
                int errorMessageTopY5 = errorMessage.getLocation().getY();
                if(errorMessageTopY5 >= usernameBottomY5){
                    result= true;
                }
                break;
            case 6:
                int usernameBottomY6 = nguoiTiepNhanInput.getLocation().getY() + nguoiTiepNhanInput.getSize().getHeight();
                int errorMessageTopY6 = errorMessage.getLocation().getY();
                if(errorMessageTopY6 >= usernameBottomY6){
                    result= true;
                }
                break;
            case 7:
                int usernameBottomY7 = ngayChungTuInput.getLocation().getY() + ngayChungTuInput.getSize().getHeight();
                int errorMessageTopY7 = errorMessage1.getLocation().getY();
                if(errorMessageTopY7 >= usernameBottomY7){
                    result= true;
                }
                break;
        }
        return result;
    }

    public boolean isAllocatonDialogDisplayed() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            return parentAssetForm.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getFirstNgayTiepNhanInDialog() {
        // Lấy phần tử dialog
        WebElement dialog = driver.findElement(By.cssSelector(".MuiDialogContent-root"));

        // Lấy bảng trong dialog
        WebElement table = dialog.findElement(By.cssSelector("table.MuiTable-root"));

        // Lấy dòng tiêu đề để tìm vị trí cột
        WebElement headerRow = table.findElement(By.cssSelector("thead tr"));
        List<WebElement> headers = headerRow.findElements(By.tagName("th"));

        // Tìm index của cột "Ngày tiếp nhận"
        int ngayTiepNhanIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText().trim();
            if (headerText.equalsIgnoreCase("Ngày tiếp nhận")) {
                ngayTiepNhanIndex = i;
                break;
            }
        }

        // Nếu không tìm thấy thì báo lỗi
        if (ngayTiepNhanIndex == -1) {
            throw new NoSuchElementException("Không tìm thấy cột 'Ngày tiếp nhận'");
        }

        // Lấy tất cả các dòng trong tbody
        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));
        if (rows.isEmpty()) {
            throw new NoSuchElementException("Không có dòng nào trong bảng.");
        }

        // Lấy dòng đầu tiên trong tbody
        WebElement firstRow = rows.get(0);
        List<WebElement> cells = firstRow.findElements(By.tagName("td"));

        // Trả về giá trị trong ô ở cột "Ngày tiếp nhận"
        return cells.get(ngayTiepNhanIndex).getText().trim();
    }

    public String lessNgayTiepNhan(){
        String increasedDate = MyUtil.subtractOneDayFromDate(getFirstNgayTiepNhanInDialog());
        return increasedDate;
    }
}
