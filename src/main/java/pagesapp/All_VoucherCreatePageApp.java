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
import utils.AssetComparator;
import utils.DataBaseUtils;
import utils.MyUtil;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.Duration;
import java.util.*;

public class All_VoucherCreatePageApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait ;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Phiếu cấp phát')]")
    private WebElement phieuCP_label;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Ngày chứng từ')]/following-sibling::android.view.View[1]")
    private WebElement ngayTaoPhieuInput;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Ngày chứng từ')]/following-sibling::android.view.View[2]")
    private WebElement ngayChungTuInput;

    @FindBy(xpath = "//android.widget.ScrollView/android.widget.Button[1]/android.view.View[1]/android.view.View")
    private WebElement phongBanBanGiaoInput;

    @FindBy(xpath = "//android.widget.ScrollView/android.widget.Button[2]")
    private WebElement nguoiBanGiaoInput;

    @FindBy(xpath = "//android.widget.ScrollView/android.widget.Button[3]")
    private WebElement trangThaiPhieuInput;

    @FindBy(xpath = "//android.widget.ScrollView/android.widget.Button[4]")
    private WebElement phongBanTiepNhanInput;

    @FindBy(xpath = "//android.widget.ScrollView/android.widget.Button[5]")
    private WebElement nguoiTiepNhanInput;

    @FindBy(xpath = "//android.widget.Button[contains(@content-desc, 'Chọn tài sản cấp phát')]")
    private WebElement chonTS;

    @FindBy(xpath = "//android.widget.Button[contains(@content-desc, 'Chọn')]")
    private WebElement chon_btn;

    @FindBy(xpath = "//android.widget.Button[contains(@content-desc, 'Lưu')]")
    private WebElement luu_btn;

    // Khởi tạo các phần tử giao diện bằng Page Factory
    public All_VoucherCreatePageApp(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getNgayTaoPhieuValue() {
        return ngayTaoPhieuInput.getText();
    }

    public boolean isNgayTaoPhieuReadonly(){
        String enabledAttribute = ngayTaoPhieuInput.getAttribute("clickable");
        if (enabledAttribute != null && enabledAttribute.equals("false")) {
            System.out.println("Thuộc tính 'enabled' là 'false', cho thấy element có thể ở trạng thái chỉ đọc.");
            return true;
        } else {
            System.out.println("Không tìm thấy thuộc tính 'enabled' hoặc nó có giá trị 'true'.");
            return false;
        }
    }

    public String getNgayChungTuValue() {
        return ngayChungTuInput.getText();
    }

    public boolean isNgayChungTuEdit(){
        String enabledAttribute = ngayChungTuInput.getAttribute("clickable");
        if (enabledAttribute != null && enabledAttribute.equals("true")) {
            System.out.println("Thuộc tính 'enabled' là 'true', cho thấy element có thể ở trạng thái chỉnh sửa.");
            return true;
        } else {
            System.out.println("Không tìm thấy thuộc tính 'enabled' hoặc nó có giá trị 'false'.");
            return false;
        }
    }

    public String getPhongBanBanGiao() {
        String phongBGApp = phongBanBanGiaoInput.getAttribute("content-desc");
        System.out.println("Phòng bàn giao trên APP:" + phongBGApp);
        return phongBGApp;
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
        List<String> data1 = MyUtil.normalizeList(data);
        System.out.println(data1);
        List<String> data2 = MyUtil.normalizeList(getAllContentDescNguoiBanGiao());
        System.out.println(data2);
        if(data1.equals(data2)){
            return true;
        }
        else
            return false;
    }

    public void clearNguoiBanGiao(){
        driver.findElements(By.xpath("//android.widget.ScrollView/android.widget.Button[2]/android.view.View[2]"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public void openDropdownTrangThaiPhieu(){
        trangThaiPhieuInput.click();
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
        List<String> options = new ArrayList<>();
        for (WebElement option : getAllElementOptionsTrangThaiPhieu()) {
            options.add(option.getAttribute("content-desc"));
        }
        return options;
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
        List<String> data1 = MyUtil.normalizeList(data);
        List<String> data2 = MyUtil.normalizeList(getAllDropdownOptionsTrangThaiPhieu());
        if(data1.equals(data2)){
            return true;
        }
        else
            return false;
    }

    public void clearTrangThaiPhieu(){
        driver.findElements(By.xpath("//android.widget.ScrollView/android.widget.Button[3]/android.view.View[2]"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public boolean isNguoiTiepNhanAble(){
        String enabledAttribute = nguoiTiepNhanInput.getAttribute("clickable");
        if (enabledAttribute != null && enabledAttribute.equals("true")) {
            System.out.println("Thuộc tính 'enabled' là 'true', cho thấy element có thể ở trạng thái chỉnh sửa.");
            return true;
        } else {
            System.out.println("Không tìm thấy thuộc tính 'enabled' hoặc nó có giá trị 'false'.");
            return false;
        }
    }

    public void openDropdownPhongBanTiepNhan() {
        phongBanTiepNhanInput.click();
    }

    public List<String> getAllContentDescPhongTiepNhan() {
        openDropdownPhongBanTiepNhan();
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

    public List<WebElement> getAllElementOptionsPhongBanTiepNhan() {
        openDropdownPhongBanTiepNhan();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ScrollView")
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

    public void clearPhongBanTiepNhan(){
        driver.findElements(By.xpath("//android.widget.ScrollView/android.widget.Button[4]/android.view.View[2]"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public void openDropdownNguoiTiepNhan() {
        nguoiTiepNhanInput.click();
    }

    public List<String> getAllContentDescNguoiTiepNhan() {
        openDropdownNguoiTiepNhan();
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

    public List<WebElement> getAllElementOptionsNguoiTiepNhan() {
        openDropdownNguoiTiepNhan();
//        List<WebElement> scrollViews;
//        WebElement scrollView = null;
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        scrollViews = driver.findElements(By.xpath("//android.widget.ScrollView"));
//        if(!scrollViews.isEmpty()) {
//                scrollView = scrollViews.get(0);
//        }else {
//            scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View")
//                    ));
//        }

        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View")
        ));
        List<WebElement> elements = scrollView.findElements(By.xpath(".//android.view.View[@content-desc]"));
        return elements;
    }

    public boolean checkNguoiTiepNhan(List<String> data){
        List<String> data1 = MyUtil.normalizeList(data);
        System.out.println(data1);
        List<String> data2 = MyUtil.normalizeList(getAllContentDescNguoiTiepNhan());
        System.out.println(data2);
        if(data1.equals(data2)){
            return true;
        }
        else
            return false;
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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

    public boolean checkDS() throws SQLException, ParseException {
        List<String> danhsach= getAllDanhsachTSCD();
        List<Asset> data1 = DataBaseUtils.getSomeAssetsAvailable(Department.DEPARTMENT_ID_AM,Allocation.STATUS_ALLOCATION_CTN,danhsach.size());
        System.out.println(data1);
        List<Asset> data2 = MyUtil.parseUiAssets(danhsach);
        System.out.println(data2);
        if(AssetComparator.compareAssetLists(data1,data2)){
            return true;
        }
        else
            return false;
    }

    public void chonTS(){
        List<WebElement> danhsach = getAllElementOptionsDanhsachTS();
        WebElement checkbox = danhsach.get(2).findElement(By.xpath(".//android.widget.CheckBox"));
        String checkedAttribute = checkbox.getAttribute("checked");
        if(checkedAttribute.equals("false")){
            danhsach.get(2).click();
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

    public boolean isAllocatonDialogDisplayed() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            return phieuCP_label.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
