package pagesapp;

import base.BaseApp;
import io.appium.java_client.android.AndroidDriver;
import model.Allocation;
import model.Asset;
import model.Department;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.Duration;
import java.util.*;

public class Transfer_VoucherCreatePageApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait ;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Phiếu điều chuyển')]")
    private WebElement phieuDC_label;
    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Ngày chứng từ')]/following-sibling::android.view.View[1]")
    private WebElement ngayTaoPhieuInput;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Ngày chứng từ')]/following-sibling::android.view.View[2]")
    private WebElement ngayChungTuInput;

    @FindBy(xpath = "//android.widget.Button[contains(@content-desc, 'Chọn tài sản')]")
    private WebElement chonTS;

    @FindBy(xpath = "//android.widget.Button[contains(@content-desc, 'Chọn')]")
    private WebElement chon_btn;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Lưu')]")
    private WebElement luu_btn;

    //may that
    @FindBy(xpath = "//android.view.View[@content-desc='Phòng bàn giao']/following-sibling::android.widget.Button")
    private WebElement phongBanBanGiaoInput;

    @FindBy(xpath = "//android.view.View[@content-desc='Người bàn giao']/following-sibling::android.widget.Button")
    private WebElement nguoiBanGiaoInput;

    @FindBy(xpath = "//android.view.View[@content-desc='Trạng thái']/following-sibling::android.widget.Button")
    private WebElement trangThaiPhieuInput;

    @FindBy(xpath = "//android.view.View[@content-desc='Phòng tiếp nhận']/following-sibling::android.widget.Button")
    private WebElement phongBanTiepNhanInput;

    @FindBy(xpath = "//android.view.View[@content-desc='Người tiếp nhận']/following-sibling::android.widget.Button")
    private WebElement nguoiTiepNhanInput;
    // Khởi tạo các phần tử giao diện bằng Page Factory
    public Transfer_VoucherCreatePageApp(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getNgayTaoPhieuValue() {
        return PageUtil.getValueText(ngayTaoPhieuInput);
    }
    public boolean isNgayTaoPhieuReadonly(){
        return PageAppUtil.isNgayTaoPhieuReadonly(ngayTaoPhieuInput);
    }

    public String getNgayChungTuValue() {
        return PageUtil.getValueText(ngayChungTuInput);
    }

    public boolean isNgayChungTuEdit(){
        return PageAppUtil.isEdit(ngayChungTuInput);
    }

    public void openDropdownTrangThaiPhieu(){
        PageUtil.clickElement(trangThaiPhieuInput);
    }

    public List<WebElement> getAllElementOptionsTrangThaiPhieu() {
        openDropdownTrangThaiPhieu();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Đợi dropdown hiển thị (thường là thẻ ul > li với class Autocomplete-option)
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.view.View")
        ));

        // Lấy toàn bộ li
        List<WebElement> optionElements = scrollView.findElements(By.xpath(".//android.view.View[@content-desc]"));
        return optionElements;
    }

    public List<String> getAllDropdownOptionsTrangThaiPhieu() {
        return PageAppUtil.getAllDropdownOptionsElements(getAllElementOptionsTrangThaiPhieu());
    }

    public void chonTrangThaiPhieuInput(int a){
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
        return MyUtil.sosanh2chuoi(data,getAllDropdownOptionsTrangThaiPhieu());
    }

    public void clearTrangThaiPhieu(){
        driver.findElements(By.xpath("//android.widget.ScrollView/android.widget.Button[1]/android.view.View[2]"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public boolean isPhongBanGiaoAble(){
        return PageAppUtil.isEdit(phongBanBanGiaoInput);
    }

    public String getPhongBanBanGiao() {
        WebElement phongBanBanGiaotext = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.View[@content-desc='Phòng bàn giao']/following-sibling::android.widget.Button//android.view.View[1]//android.view.View")
        ));
        return PageAppUtil.getValueContent(phongBanBanGiaotext);
    }

    public boolean checkPhongBanBanGiaoFirstly(String data){
        if(data.equals(getPhongBanBanGiao())){
            return true;
        }
        else
            return false;
    }

    public void openDropdownPhongBanBanGiao() {
        phongBanBanGiaoInput.click();
    }

    public List<String> getAllContentDescPhongBanGiao() {
        openDropdownPhongBanBanGiao();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ScrollView")
        ));

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

    public boolean checkPhongBanBanGiao(List<String> data){
        List<String> danhsach = getAllContentDescPhongBanGiao();
        System.out.println("DB: " + data.size());
        System.out.println("APP: " + danhsach.size());
        if(data.size() == (danhsach.size())){
            return true;
        }
        else
            return false;
    }

    public void chonPhongBanBanGiaoInput(int a){
        openDropdownPhongBanBanGiao();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]")
        ));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement editText = scrollView.findElement(By.xpath(".//android.widget.EditText"));
        editText.click();
        if(a == 2){
            editText.sendKeys(Department.DEPARTMENT_NAME_AU1);}
        else
            editText.sendKeys(Department.DEPARTMENT_NAME_AM);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> elements = scrollView.findElements(By.xpath(".//android.view.View[@content-desc]"));
        elements.getFirst().click();
    }

    public void clearPhongBanBanGiao(){
        driver.findElements(By.xpath("//android.view.View[@content-desc='Phòng bàn giao']/following-sibling::android.widget.Button//android.view.View[2]"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public boolean isNguoiBanGiaoAble(){
        return PageAppUtil.isEdit(nguoiBanGiaoInput);
    }

    public void openDropdownNguoiBanGiao() {
        nguoiBanGiaoInput.click();
    }

    public List<WebElement> getAllElementOptionsNguoiBanGiao() {
        openDropdownNguoiBanGiao();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ScrollView")
        ));
        List<WebElement> elements = scrollView.findElements(By.xpath(".//android.view.View[@content-desc]"));
        return elements;
    }

    public List<String> getAllContentDescNguoiBanGiao() {
        openDropdownNguoiBanGiao();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ScrollView")
        ));

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

    public void chonNguoiBanGiaoInput(){
        getAllElementOptionsNguoiBanGiao().get(1).click();
    }

    public boolean checkNguoiBanGiao(List<String> data){
        return MyUtil.sosanh2chuoi(data,getAllContentDescNguoiBanGiao());
    }

    public void clearNguoiBanGiao(){
        driver.findElements(By.xpath("//android.widget.ScrollView/android.widget.Button[3]/android.view.View[2]"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public String getPhongBanTiepNhan() {
        WebElement phongBanTiepNhantext = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.View[@content-desc='Phòng tiếp nhận']/following-sibling::android.widget.Button//android.view.View[1]//android.view.View")
        ));
        return PageAppUtil.getValueContent(phongBanTiepNhantext);
    }
    public void openDropdownPhongBanTiepNhan() {
        PageUtil.clickElement(phongBanTiepNhanInput);
    }

    public List<String> getAllContentDescPhongTiepNhan() {
        openDropdownPhongBanTiepNhan();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ScrollView")
        ));

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
        openDropdownPhongBanTiepNhan();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]")
        ));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement editText = scrollView.findElement(By.xpath(".//android.widget.EditText"));
        editText.click();
        editText.sendKeys(Department.DEPARTMENT_NAME_AU1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> elements = scrollView.findElements(By.xpath(".//android.view.View[@content-desc]"));
        elements.getFirst().click();
    }

    public void clearPhongBanTiepNhan(){
        driver.findElements(By.xpath("//android.view.View[@content-desc='Phòng tiếp nhận']/following-sibling::android.widget.Button//android.view.View[2]"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public boolean isNguoiTiepNhanAble(){
        return PageAppUtil.isEdit(nguoiTiepNhanInput);
    }
    public void openDropdownNguoiTiepNhan() {
        nguoiTiepNhanInput.click();
    }

    public List<String> getAllContentDescNguoiTiepNhan() {
        openDropdownNguoiTiepNhan();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ScrollView")
        ));

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

    public List<WebElement> getAllElementOptionsNguoiTiepNhan() {
        openDropdownNguoiTiepNhan();
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View")
        ));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> elements = scrollView.findElements(By.xpath(".//android.view.View[@content-desc]"));
        return elements;
    }

    public boolean checkNguoiTiepNhan(List<String> data){
       return MyUtil.sosanh2chuoi(data,getAllContentDescNguoiTiepNhan());
    }

    public String getNguoiTiepNhanInput() {
        WebElement element = nguoiTiepNhanInput.findElement(By.xpath(".//android.view.View[@content-desc]"));
        return element.getAttribute("content-desc");
    }
    public void chonNguoiTiepNhanInput(){
        getAllElementOptionsNguoiTiepNhan().get(1).click();
    }
    public void clearNguoiTiepNhan(){
        driver.findElements(By.xpath("//android.widget.ScrollView/android.widget.Button[5]/android.view.View[2]"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public void openDialogChonTS() {
        chonTS.click();
    }
    public List<String> getAllDanhsachTSCD() {
        openDialogChonTS();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ScrollView")
        ));
        List<String> contentDescs = new ArrayList<>();
        List<WebElement> visibleElements = scrollView.findElements(By.xpath(".//android.widget.Button[@content-desc]"));
        for (WebElement el : visibleElements) {
            contentDescs.add(el.getAttribute("content-desc"));
        }

        return new ArrayList<>(contentDescs);
    }

    public List<WebElement> getAllElementOptionsDanhsachTS() {
        openDialogChonTS();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ScrollView")
        ));
        List<WebElement> elements = scrollView.findElements(By.xpath(".//android.widget.Button[@content-desc]"));
        return elements;
    }

    public boolean checkDS(String data) throws SQLException, ParseException {
        List<String> danhsach= getAllDanhsachTSCD();
        List<Asset> data1 = DataBaseUtils.getSomeAssetsAvailableDC(data,danhsach.size());
        System.out.println(data1);
        List<Asset> data2 = MyUtil.parseUiAssets(danhsach);
        System.out.println(data2);
        if(AssetComparator.compareAssetLists(data1,data2)){
            return true;
        }
        else
            return false;
    }

    public boolean checkDSHanhChinh() throws SQLException, ParseException {
        openDialogChonTS();
        WebElement droplist = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.View[@content-desc='Phòng ban quản lý']/following-sibling::android.widget.Button[1]")
        ));
        droplist.click();
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]")
        ));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement editText = scrollView.findElement(By.xpath(".//android.widget.EditText"));
        editText.click();
        editText.sendKeys("hành chính");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> elements = scrollView.findElements(By.xpath(".//android.view.View[@content-desc]"));
        elements.getFirst().click();
        List<WebElement> taisan = Collections.singletonList(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.View[@content-desc='Không tìm thấy tài sản']")
        )));
        if(taisan.isEmpty()){
            return false;
        } else
            return true;
    }

    public void chonTS(){
        List<WebElement> danhsach = getAllElementOptionsDanhsachTS();
        WebElement checkbox = danhsach.get(0).findElement(By.xpath(".//android.widget.CheckBox"));
        String checkedAttribute = checkbox.getAttribute("checked");
        if(checkedAttribute.equals("false")){
            danhsach.get(0).click();
        }
        chon_btn.click();
    }

    public void clearTS(){
        List<WebElement> danhsach = getAllElementOptionsDanhsachTS();
        WebElement checkbox = danhsach.get(2).findElement(By.xpath(".//android.widget.CheckBox"));
        String checkedAttribute = checkbox.getAttribute("checked");
        if(checkedAttribute.equals("true")){
            danhsach.get(2).click();
        }
        chon_btn.click();
    }

    public void setLuu_btn(){
        luu_btn.click();
    }
    public boolean isTransferDialogDisplayed() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            return phieuDC_label.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
