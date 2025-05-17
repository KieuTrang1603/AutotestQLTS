package pagesweb;

import drivers.DriverManager;
import model.Allocation;
import model.Department;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.MyUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class All_VoucherPageWeb {
    private final WebDriver driver;
    private final WebDriverWait wait;
    Allocation allocation;

    @FindBy(xpath = "//button//span[@class='MuiButton-label']")
    private List<WebElement> menuButtons;

    @FindBy(xpath = "//p[contains(@class, 'MuiTablePagination-caption') and contains(text(), 'trong')]")
    private WebElement paginationText;

    @FindBy(css=(".Toastify__toast--success"))
    private WebElement toastMessage;

    @FindBy(xpath = "//button//span[text()='TK nâng cao']")
    private WebElement searchhigh;

    // Constructor
    public All_VoucherPageWeb(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateToAllocation_VoucherPage(String user, String password){
        LoginPageWeb loginPageWeb = new LoginPageWeb(driver);
        HomePageWeb homePageWeb = new HomePageWeb(driver);
        loginPageWeb.navigateToLoginPage();
        loginPageWeb.login(user, password);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        homePageWeb.Allocation_voucher_ui();
    }

    public void closeMenu(){
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();
    }

    public void All_Btn_click(){
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector(\"div[aria-hidden='true'][style*='z-index: -1']\")?.remove();"
        );

        // Bước 3: Đảm bảo allocation_btn đã sẵn sàng
        WebElement allocation_btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button//span[contains(text(), 'Thêm mới')]")
                ));
        // Click bằng JS để đảm bảo không bị lớp nào cản
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", allocation_btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", allocation_btn);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Kiem tra thanh menu hien thi dung k
    public boolean isMenuDisplayedCorrectly(List <String> expectedMenus){
        if (menuButtons.size() != expectedMenus.size()) {
            System.out.println("Số lượng menu không khớp! Mong đợi: " + expectedMenus.size() + ", Thực tế: " + menuButtons.size());
            return false;
        }

        // Kiểm tra từng menu có tồn tại không
        for (String expectedMenu : expectedMenus) {
            boolean menuExists = menuButtons.stream().anyMatch(button -> button.getText().trim().equalsIgnoreCase(expectedMenu));
            if (!menuExists) {
                System.out.println("Không tìm thấy menu: " + expectedMenu);
                return false;
            }
        }
        return true;
    }

    public String getPaginationText() {
        paginationText.getText();
        System.out.println("Text lấy được: " + paginationText);
        return paginationText.getText();
    }

    public boolean checkSobanghi(Integer sobanghi){
        System.out.println("Dữ liệu lấy ra: " + getPaginationText());
        String total = MyUtil.getSobangi(getPaginationText());
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

    public String getToastMessageText() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOf(toastMessage));
        return toast.getText();
    }

    public void Nhap_Btn_click(){
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector(\"div[aria-hidden='true'][style*='z-index: -1']\")?.remove();"
        );

        // Bước 3: Đảm bảo allocation_btn đã sẵn sàng
        WebElement allocation_btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button//span[contains(text(), 'Nhập Excel')]")
                ));
        // Click bằng JS để đảm bảo không bị lớp nào cản
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", allocation_btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", allocation_btn);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Allocation> getAllocationRecord(){

        WebElement firstTable = driver.findElement(
                By.xpath("(//table[contains(@class, 'MuiTable-root')])[1]")
        );
        List<WebElement> rows = firstTable.findElements(By.xpath(".//tbody/tr"));
        List<Allocation> list = new ArrayList<>();

        for (WebElement row : rows) {
            String ngay = row.findElement(By.xpath("./td[3]")).getText().trim();
            String trangThai = row.findElement(By.xpath("./td[4]")).getText().trim();
            String phongGiao = row.findElement(By.xpath("./td[5]")).getText().trim();
            String nguoiGiao = row.findElement(By.xpath("./td[6]")).getText().trim();
            String phongNhan = row.findElement(By.xpath("./td[7]")).getText().trim();
            String nguoiNhan = row.findElement(By.xpath("./td[8]")).getText().trim();

            Allocation p = new Allocation("", "", "", "", "", "");
            p.setNgay(ngay);
            p.setTrangThai(trangThai);
            p.setPhongGiao(phongGiao);
            p.setNguoiGiao(nguoiGiao);
            p.setPhongNhan(phongNhan);
            p.setNguoiNhan(nguoiNhan);
            list.add(p);
        }
        return list;
    }

    public void searchAsset(String ma){
        WebElement inputSearch = driver.findElement(By.id("search_box")); // hoặc sửa thành id thực tế nếu khác
        inputSearch.clear();
        inputSearch.sendKeys(ma);
        inputSearch.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchHigh(int a, String tenPTN){
        searchhigh.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement trangThai = searchhigh.findElement(By.xpath("//label[.//text()[contains(.,'Trạng thái')]]/following::input[1]"));
        trangThai.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@id, '-popup')]")));
        List<WebElement> trangThaiItems = trangThai.findElements(By.xpath("//ul[contains(@id, '-popup')]/li"));
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
        WebElement phongTN = searchhigh.findElement(By.xpath("//label[.//text()[contains(.,'Phòng ban tiếp nhận')]]/following::input[1]"));
        phongTN.click();
        phongTN.sendKeys(tenPTN);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@id, '-popup')]")));
        List<WebElement> phongTNItems = trangThai.findElements(By.xpath("//ul[contains(@id, '-popup')]/li"));
        phongTNItems.get(0).click();
        WebElement denNgay = searchhigh.findElement(By.xpath("//label[.//text()[contains(.,'Đến ngày')]]/following::input[1]"));
        denNgay.click();
        denNgay.sendKeys(MyUtil.currentDate);
    }

    public Allocation getDuLieuCP(){
        WebElement table = driver.findElement(By.cssSelector("table.MuiTable-root"));

        // Lấy dòng tiêu đề để tìm vị trí cột
        WebElement headerRow = table.findElement(By.cssSelector("thead tr"));
        List<WebElement> headers = headerRow.findElements(By.tagName("th"));

        // Tìm index của cột "Phòng bàn giao"
        int phongBanGiaoIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText().trim();
            if (headerText.equalsIgnoreCase("Phòng ban bàn giao")) {
                phongBanGiaoIndex = i;
                break;
            }
        }
        // Nếu không tìm thấy thì báo lỗi
        if (phongBanGiaoIndex == -1) {
            throw new NoSuchElementException("Không tìm thấy cột 'Phòng ban bàn giao'");
        }

        // Tìm index của cột "Phòng tiếp nhận"
        int phongSuDungIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText().trim();
            if (headerText.equalsIgnoreCase("Phòng ban tiếp nhận")) {
                phongSuDungIndex = i;
                break;
            }
        }
        // Nếu không tìm thấy thì báo lỗi
        if (phongSuDungIndex == -1) {
            throw new NoSuchElementException("Không tìm thấy cột 'Phòng ban tiêp nhận'");
        }

        // Lấy tất cả các dòng trong tbody
        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));
        if (rows.isEmpty()) {
            throw new NoSuchElementException("Không có dòng nào trong bảng.");
        }
        // Lấy dòng đầu tiên trong tbody
        WebElement firstRow = rows.get(0);
        List<WebElement> cells = firstRow.findElements(By.tagName("td"));
        allocation = new Allocation();
        allocation.setPhongGiao(cells.get(phongBanGiaoIndex).getText());
        allocation.setPhongNhan(cells.get(phongSuDungIndex).getText());
        return allocation;
    }
    public boolean checkBanghiCapphat(String ma, int a, String PBBG, String PBTN){
        searchAsset(ma);
        searchHigh(a,PBTN);
        getDuLieuCP();
        boolean check = false;
        if(allocation.getPhongGiao().contains(PBBG) && allocation.getPhongNhan().contains(PBTN)){
            check =true;
            return check;
        } else
            return check;
    }
    public String getSessionIdCookie() {
        Cookie jsessionid = driver.manage().getCookieNamed("JSESSIONID");
        return jsessionid != null ? "JSESSIONID=" + jsessionid.getValue() : "";
    }
}
