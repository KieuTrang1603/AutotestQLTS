package pagesweb;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.MyUtil;

import java.time.Duration;
import java.util.List;

public class DS_TSCD_Maintain_Dialog {
    private final WebDriver driver;
    private final WebDriverWait wait;
    Main_TainReportWeb reportWeb;
    String soluong;
    @FindBy(xpath = "//button//span[text()='Đóng']")
    private WebElement huy_btn;
    @FindBy(xpath = "//button//span[text()='Chọn']")
    private WebElement chon_btn;

    public DS_TSCD_Maintain_Dialog(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public WebElement openDialogChonTS(){
        reportWeb=new Main_TainReportWeb(driver);
        reportWeb.openDialogTS();
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
        return activeDialog;
    }
    public void getPaginationText(){
        // 3. Tìm phần tử phân trang trong dialog đang mở
        WebElement paginationTextElement = openDialogChonTS().findElement(
                By.xpath("//div[contains(@class, 'MuiDialogContent-root')]//p[contains(@class, 'MuiTablePagination-caption') and contains(text(), 'trong')]")
        );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String paginationText = paginationTextElement.getText();
        System.out.println("Text lấy được: " + paginationText);
        soluong=paginationText;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        huy_btn.click();
    }


    public boolean checkSobanghi(Integer sobanghi){
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

    public void chonTSCD(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dialog = openDialogChonTS();

        // Chờ cho ít nhất 1 radio button xuất hiện trong dialog
        List<WebElement> paginationTextElement = wait.until(driver -> {
            List<WebElement> radios = dialog.findElements(By.cssSelector("input[type='radio']"));
            return radios.size() > 0 ? radios : null;
        });

        // Sau khi đã chắc chắn có phần tử, bạn mới click
        paginationTextElement.get(0).click();
        chon_btn.click();
    }

    public String chonVaLayTSTuDialog(){
        WebElement dialog = openDialogChonTS();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='radio']"))
        );
        List<WebElement> paginationTextElement = dialog.findElements(
                By.cssSelector("input[type='radio']"));

        if (!paginationTextElement.isEmpty()) {
            paginationTextElement.get(0).click();
        } else {
            throw new NoSuchElementException("Không tìm thấy checkbox nào trong dialog chọn TS.");
        }
        WebElement table = dialog.findElement(By.cssSelector("table.MuiTable-root"));
        // Lấy dòng tiêu đề để tìm vị trí cột
        WebElement headerRow = table.findElement(By.cssSelector("thead tr"));
        List<WebElement> headers = headerRow.findElements(By.tagName("th"));

        // Tìm index của cột "Ma tai san"
        int maTaiSanIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText().trim();
            if (headerText.equalsIgnoreCase("Mã tài sản")) {
                maTaiSanIndex = i;
                break;
            }
        }

        // Nếu không tìm thấy thì báo lỗi
        if (maTaiSanIndex == -1) {
            throw new NoSuchElementException("Không tìm thấy cột 'Mã tài sản'");
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
        return cells.get(maTaiSanIndex).getText().trim();
    }

    public void ChosenTS(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        chon_btn.click();
    }
}
