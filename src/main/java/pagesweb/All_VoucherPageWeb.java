package pagesweb;

import drivers.DriverManager;
import model.AllocationRecord;
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

    @FindBy(xpath = "//button//span[@class='MuiButton-label']")
    private List<WebElement> menuButtons;

    @FindBy(xpath = "//p[contains(@class, 'MuiTablePagination-caption') and contains(text(), 'trong')]")
    private WebElement paginationText;

    @FindBy(css=(".Toastify__toast--success"))
    private WebElement toastMessage;

    String file_capphat ="D:\\Tester\\Auto\\Selenium\\Login\\File";

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
                        By.xpath("//button//span[contains(text(), 'Cấp phát TSCĐ')]")
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

    public List<AllocationRecord> getAllocationRecord(){

        WebElement firstTable = driver.findElement(
                By.xpath("(//table[contains(@class, 'MuiTable-root')])[1]")
        );
        List<WebElement> rows = firstTable.findElements(By.xpath(".//tbody/tr"));
        List<AllocationRecord> list = new ArrayList<>();

        for (WebElement row : rows) {
            String ngay = row.findElement(By.xpath("./td[3]")).getText().trim();
            String trangThai = row.findElement(By.xpath("./td[4]")).getText().trim();
            String phongGiao = row.findElement(By.xpath("./td[5]")).getText().trim();
            String nguoiGiao = row.findElement(By.xpath("./td[6]")).getText().trim();
            String phongNhan = row.findElement(By.xpath("./td[7]")).getText().trim();
            String nguoiNhan = row.findElement(By.xpath("./td[8]")).getText().trim();

            AllocationRecord p = new AllocationRecord("", "", "", "", "", "");
            p.ngay = ngay;
            p.trangThai = trangThai;
            p.phongGiao = phongGiao;
            p.nguoiGiao = nguoiGiao;
            p.phongNhan = phongNhan;
            p.nguoiNhan = nguoiNhan;
            list.add(p);
        }
        return list;
    }

}
