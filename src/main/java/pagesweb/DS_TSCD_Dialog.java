package pagesweb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.MyUtil;

import java.time.Duration;
import java.util.List;

public class DS_TSCD_Dialog {
    private final WebDriver driver;
    private final WebDriverWait wait;
    All_VoucherCreatePageWeb all;
    String soluong;
    @FindBy(xpath = "//button//span[text()='Hủy']")
    private WebElement huy_btn;
    @FindBy(xpath = "//button//span[text()='Chọn']")
    private WebElement chon_btn;

    public DS_TSCD_Dialog(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public WebElement openDialogChonTS(){
        all=new All_VoucherCreatePageWeb(driver);
        all.openDialogTS();
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
        List<WebElement> paginationTextElement = openDialogChonTS().findElements(
                By.cssSelector("input[type='checkbox']")
        );
        for (int i = 0; i < Math.min(2, paginationTextElement.size()); i++) {
            paginationTextElement.get(i).click();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        chon_btn.click();
    }
}
